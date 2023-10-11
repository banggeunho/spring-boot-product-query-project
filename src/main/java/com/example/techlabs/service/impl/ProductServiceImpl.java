package com.example.techlabs.service.impl;

import com.example.techlabs.base.common.CommonUtil;
import com.example.techlabs.base.csv.ProductCsvBean;
import com.example.techlabs.repository.ProductJdbcRepository;
import com.example.techlabs.repository.ProductJpaRepository;
import com.example.techlabs.repository.ProductRelationJpaRepository;
import com.example.techlabs.repository.entity.ProductEntity;
import com.example.techlabs.service.ProductService;
import com.example.techlabs.service.vo.command.ProductCommandVO;
import com.example.techlabs.service.vo.command.ProductCommandVOList;
import com.example.techlabs.service.vo.query.ProductQueryVO;
import com.example.techlabs.service.vo.query.ProductQueryVOList;
import com.example.techlabs.service.vo.query.RelatedProductInfoVO;
import com.example.techlabs.service.vo.query.RelatedProductInfoVOList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductJdbcRepository productJdbcRepository;
    private final ProductJpaRepository productJpaRepository;
    private final ProductRelationJpaRepository productRelationJpaRepository;

    @Override
    public void saveAll(List<ProductCsvBean> productCsvBeanList) {
        productJdbcRepository.saveAll(
                productCsvBeanList.stream()
                        .map(x -> ProductEntity.builder()
                                .itemId(x.getItemId())
                                .itemName(x.getItemName())
                                .originalPrice(x.getOriginalPrice())
                                .salePrice(x.getSalePrice())
                                .itemDescriptionUrl(x.getItemDescriptionUrl())
                                .itemImageUrl(x.getItemImageUrl())
                                .createdBy("SYSTEM")
                                .lastModifiedBy("SYSTEM")
                                .isDeleted(false)
                                .build())
                        .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    @Override
    public ProductQueryVOList findAll() {
        return ProductQueryVOList.of(productJpaRepository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public ProductQueryVOList findByInIdList(List<Long> targetIdList) {

        List<ProductEntity> productEntityList = productJpaRepository.findByItemIdInAndIsDeletedJoinRelationship(targetIdList, false);

        checkProductExistence(targetIdList, CommonUtil.groupByKey(productEntityList, ProductEntity::getItemId));

        return ProductQueryVOList.builder()
                .productQueryVOList(productEntityList.stream()
                        .map(productEntity -> ProductQueryVO.builder()
                                .itemId(productEntity.getItemId())
                                .itemName(productEntity.getItemName())
                                .itemImageUrl(productEntity.getItemImageUrl())
                                .itemDescriptionUrl(productEntity.getItemDescriptionUrl())
                                .originalPrice(productEntity.getOriginalPrice())
                                .salePrice(productEntity.getSalePrice())
                                .relatedProductInfoVOList(mapRelatedItemInfo(productEntity.getResultProductInfos()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public ProductQueryVOList save(ProductCommandVOList productCommandVOList) {

        List<Long> idList = CommonUtil.extractKey(productCommandVOList.getProductCommandVOList(), ProductCommandVO::getItemId);

        List<ProductEntity> productEntityList = productJpaRepository.findByItemIdInAndIsDeleted(idList,false);

        Map<Long, ProductEntity> productEntityMap = productEntityList.stream()
                .collect(Collectors.toMap(ProductEntity::getItemId, Function.identity()));

        productEntityList.stream()
                .filter(productEntity -> productEntityMap.containsKey(productEntity.getItemId()))
                .findFirst()
                .ifPresent(productEntity -> { throw new RuntimeException(String.format("해당 상품이 이미 존재합니다.", productEntity.getItemId()));});

        productJdbcRepository.saveAll(productCommandVOList.getProductCommandVOList().stream()
                .map(ProductCommandVO::toEntity)
                .collect(Collectors.toList()));

        return ProductQueryVOList.of(productCommandVOList);
    }

    @Override
    public void delete(List<Long> itemIdList) {
        List<ProductEntity> productEntityList = productJpaRepository.findByItemIdInAndIsDeleted(itemIdList, false);

        checkProductExistence(itemIdList, CommonUtil.groupByKey(productEntityList, ProductEntity::getItemId));

        productJpaRepository.bulkUpdateIsDeleted(itemIdList);
        productRelationJpaRepository.bulkUpdateIsDeleted(itemIdList);
    }

    @Transactional(readOnly = true) // jpa의 dirty checking을 disable 하기 위해 readonly 활성화
    @Override
    public ProductQueryVOList update(ProductCommandVOList voList) {
        // 한방 쿼리 버전
        List<Long> idList = CommonUtil.extractKey(voList.getProductCommandVOList(), ProductCommandVO::getId);
        List<ProductEntity> productEntityList = productJpaRepository.findByItemIdInAndIsDeleted(idList,false);
        Map<Long, ProductEntity> productEntityMap = CommonUtil.groupByKey(productEntityList, ProductEntity::getItemId);

        checkProductExistence(idList, productEntityMap);

        voList.getProductCommandVOList().forEach(vo -> productEntityMap.get(vo.getItemId()).update(vo));
        productJdbcRepository.updateProductInfo(productEntityList);

        return ProductQueryVOList.of(productEntityList);
    }

    private void checkProductExistence(List<Long> targetIdList, Map<Long, ProductEntity> productEntityMap) {
        targetIdList.stream()
                .filter(id -> !productEntityMap.containsKey(id))
                .findFirst()
                .ifPresent(id -> {throw new RuntimeException(String.format("해당 품목이 존재하지 않습니다. {}", id));});
    }

    private RelatedProductInfoVOList mapRelatedItemInfo(List<ProductEntity.ResultProductInfo> resultProductInfoList) {

        List<Long> resultIdList = CommonUtil.extractKey(resultProductInfoList, ProductEntity.ResultProductInfo::getItemId);

        return RelatedProductInfoVOList.builder()
                .voList(productJpaRepository.findByItemIdInAndIsDeleted(resultIdList, false).stream()
                        .map(entity -> RelatedProductInfoVO.builder()
                                .itemId(entity.getItemId())
                                .itemName(entity.getItemName())
                                .itemImageUrl(entity.getItemImageUrl())
                                .itemDescriptionUrl(entity.getItemDescriptionUrl())
                                .originalPrice(entity.getOriginalPrice())
                                .salePrice(entity.getSalePrice())
                                .score(getScoreByItemId(resultProductInfoList, entity.getItemId()))
                                .rank(getRankByItemId(resultProductInfoList, entity.getItemId()))
                                .build())
                        .sorted(Comparator.comparing(RelatedProductInfoVO::getRank))
                        .collect(Collectors.toList()))
                .build();
    }

    private BigDecimal getScoreByItemId(List<ProductEntity.ResultProductInfo> resultProductInfoList, Long itemId) {
        Optional<ProductEntity.ResultProductInfo> result = resultProductInfoList.stream()
                .filter(x -> Objects.equals(x.getItemId(), itemId))
                .findFirst();

        if (result.isPresent()) {
            return result.get().getScore();
        } else {
            return BigDecimal.ZERO; // todo 예외처리
        }
    }

    private Long getRankByItemId(List<ProductEntity.ResultProductInfo> resultProductInfoList, Long itemId) {
        Optional<ProductEntity.ResultProductInfo> result = resultProductInfoList.stream()
                .filter(x -> Objects.equals(x.getItemId(), itemId))
                .findFirst();

        if (result.isPresent()) {
            return result.get().getRank();
        } else {
            return 0L; // todo 예외처리
        }
    }
}
