package com.example.techlabs.service.impl;

import com.example.techlabs.base.common.CommonUtil;
import com.example.techlabs.base.csv.ProductCsvBean;
import com.example.techlabs.repository.entity.ProductEntity;
import com.example.techlabs.repository.entity.ProductRelationshipEntity;
import com.example.techlabs.repository.ProductJdbcRepository;
import com.example.techlabs.repository.ProductJpaRepository;
import com.example.techlabs.service.ProductService;
import com.example.techlabs.service.vo.command.ProductCommandVO;
import com.example.techlabs.service.vo.query.ProductQueryVO;
import com.example.techlabs.service.vo.query.ProductQueryVOList;
import com.example.techlabs.service.vo.query.RelatedProductInfoVO;
import com.example.techlabs.service.vo.query.RelatedProductInfoVOList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductJdbcRepository productJdbcRepository;
    private final ProductJpaRepository productJpaRepository;

    @Override
    public int saveAll(List<ProductCsvBean> productCsvBeans) {
        return productJdbcRepository.saveAll(
                productCsvBeans.stream()
                        .map(x -> ProductEntity.builder()
                                .itemId(x.getItemId())
                                .itemName(x.getItemName())
                                .originalPrice(x.getOriginalPrice())
                                .salePrice(x.getSalePrice())
                                .itemDescriptionUrl(x.getItemDescriptionUrl())
                                .itemImageUrl(x.getItemImageUrl())
                                .createdBy("SYSTEM")
                                .lastModifiedBy("SYSTEM")
                                .isDel(false)
                                .build())
                        .collect(Collectors.toList()));
    }

    @Override
    public ProductQueryVOList findAll() {
        return ProductQueryVOList.builder()
                .productQueryVOList(
                        productJpaRepository.findAll().stream()
                                .filter(x -> !x.getIsDel())
                                .map(x -> ProductQueryVO.builder()
                                        .id(x.getId())
                                        .itemId(x.getItemId())
                                        .itemName(x.getItemName())
                                        .itemImageUrl(x.getItemImageUrl())
                                        .itemDescriptionUrl(x.getItemDescriptionUrl())
                                        .originalPrice(x.getOriginalPrice())
                                        .salePrice(x.getSalePrice())
                                        .relatedProductInfoVOList(mapRelatedItemInfo(x.getRelatedProducts()))
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public ProductQueryVOList findByInIdList(List<Long> targetIdList) {
        return ProductQueryVOList.builder()
                .productQueryVOList(
                        productJpaRepository.findByItemIdInAndIsDeleted(targetIdList, false).stream()
                                .map(productEntity -> ProductQueryVO.builder()
                                        .itemId(productEntity.getItemId())
                                        .itemName(productEntity.getItemName())
                                        .itemImageUrl(productEntity.getItemImageUrl())
                                        .itemDescriptionUrl(productEntity.getItemDescriptionUrl())
                                        .originalPrice(productEntity.getOriginalPrice())
                                        .salePrice(productEntity.getSalePrice())
                                        .relatedProductInfoVOList(mapRelatedItemInfo(productEntity.getRelatedProducts()))
                                        .build())
                                .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ProductQueryVO save(ProductCommandVO productCommandVO) {
        productJpaRepository.findByItemId(productCommandVO.getItemId())
                .ifPresent(product -> { throw new RuntimeException("Product with the specified item ID already exists."); });
        //todo 예외처리 변경
        return ProductQueryVO.of(productJpaRepository.save(ProductCommandVO.toEntity(productCommandVO)));
    }

    private RelatedProductInfoVOList mapRelatedItemInfo(List<ProductRelationshipEntity> productRelationshipEntities) {
        log.debug("mapping related Product");
        return RelatedProductInfoVOList.builder()
                .voList(productJpaRepository.findByItemIdInAndIsDeleted(productRelationshipEntities.stream()
                                        .map(ProductRelationshipEntity::getResultItemId)
                                        .collect(Collectors.toList()),
                                false).stream()
                        .map(resultProduct -> RelatedProductInfoVO.builder()
                                .itemId(resultProduct.getItemId())
                                .itemName(resultProduct.getItemName())
                                .itemImageUrl(resultProduct.getItemImageUrl())
                                .itemDescriptionUrl(resultProduct.getItemDescriptionUrl())
                                .originalPrice(resultProduct.getOriginalPrice())
                                .salePrice(resultProduct.getSalePrice())
                                .score(CommonUtil.findByKey(productRelationshipEntities, x -> x.getResultItemId().equals(resultProduct.getItemId())).getScore())
                                .rank(CommonUtil.findByKey(productRelationshipEntities, x -> x.getResultItemId().equals(resultProduct.getItemId())).getRank())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
