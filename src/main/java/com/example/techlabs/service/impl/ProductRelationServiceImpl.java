package com.example.techlabs.service.impl;

import com.example.techlabs.base.csv.ProductRelationshipCsvBean;
import com.example.techlabs.repository.ProductJpaRepository;
import com.example.techlabs.repository.ProductRelationJpaRepository;
import com.example.techlabs.repository.ProductRelationshipJdbcRepository;
import com.example.techlabs.repository.entity.ProductEntity;
import com.example.techlabs.repository.entity.ProductRelationshipEntity;
import com.example.techlabs.service.ProductRelationService;
import com.example.techlabs.service.vo.command.ProductRelationCommandVO;
import com.example.techlabs.service.vo.query.ProductQueryVOList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRelationServiceImpl implements ProductRelationService {

    private final ProductRelationshipJdbcRepository productRelationshipJdbcRepository;
    private final ProductRelationJpaRepository productRelationJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    @Override
    public void saveAll(List<ProductRelationshipCsvBean> productRelationshipCsvBeans, ProductQueryVOList productQueryVOList) {
        productRelationshipJdbcRepository.saveAll(
                productRelationshipCsvBeans.stream()
                        .filter(x -> productQueryVOList.isExistByItemId(x.getTargetItemId()))
                        .map(x -> ProductRelationshipEntity.builder()
                                .targetProduct(productQueryVOList.getByTargetItemId(x.getTargetItemId()).toEntity())
                                .resultItemId(x.getResultItemId())
                                .score(x.getScore())
                                .rank(x.getRank())
                                .isDeleted(false)
                                .createdBy("SYSTEM")
                                .lastModifiedBy("SYSTEM")
                                .build())
                        .collect(Collectors.toList())
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductRelationCommandVO save(ProductRelationCommandVO vo) {

        ProductEntity targetProduct = productJpaRepository.findByItemIdAndIsDeletedWithJoin(vo.getTargetItemId(), false)
                .orElseThrow(() -> new RuntimeException("해당 타겟 상품이 없습니다요."));

        ProductEntity resultProduct = productJpaRepository.findByItemIdAndIsDeleted(vo.getResultItemId(), false)
                .orElseThrow(() -> new RuntimeException("해당 연관 상품이 없습니다요."));

        productRelationJpaRepository.findByTargetProductAndResultItemIdAndIsDeleted(targetProduct, resultProduct.getItemId(), false)
                .ifPresent(x -> { throw new RuntimeException("해당 관계가 이미 존재합니다.");});

        ProductRelationshipEntity productRelationshipEntity = ProductRelationshipEntity.builder()
                .score(vo.getScore())
                .targetProduct(targetProduct)
                .resultItemId(resultProduct.getItemId())
                .isDeleted(false)
                .build();

        targetProduct.getRelatedProducts().add(productRelationshipEntity);

        return ProductRelationCommandVO.builder()
                .targetItemId(vo.getTargetItemId())
                .resultItemId(vo.getResultItemId())
                .score(vo.getScore())
                .rank(updateRanking(targetProduct, vo.getResultItemId()))
                .build();
    }

    private long updateRanking(ProductEntity targetProduct, Long resultProductItemId) {
        List<ProductRelationshipEntity> relationshipEntitieList = targetProduct.getRelatedProducts().stream()
                .sorted(Comparator.comparing(ProductRelationshipEntity::getScore).reversed())
                .collect(Collectors.toList());

        long rank = 1L;
        long newRank = -1L;
        for (ProductRelationshipEntity relationshipEntity : relationshipEntitieList) {
            if (Objects.equals(relationshipEntity.getResultItemId(), resultProductItemId)) {
                newRank = rank;
            }
            relationshipEntity.setRank(rank);
            rank ++;
        }

        productRelationshipJdbcRepository.updateRanking(relationshipEntitieList);

        return newRank;
    }
}
