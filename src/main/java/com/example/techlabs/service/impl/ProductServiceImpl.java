package com.example.techlabs.service.impl;

import com.example.techlabs.base.csv.ProductCsvBean;
import com.example.techlabs.repository.ProductJdbcRepository;
import com.example.techlabs.repository.ProductJpaRepository;
import com.example.techlabs.repository.entity.ProductEntity;
import com.example.techlabs.repository.entity.ProductRelationshipEntity;
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
                                .isDeleted(false)
                                .build())
                        .collect(Collectors.toList()));
    }

    @Override
    public ProductQueryVOList findAll() {
        return ProductQueryVOList.builder()
                .productQueryVOList(
                        productJpaRepository.findAll().stream()
                                .filter(x -> !x.getIsDeleted())
                                .map(x -> ProductQueryVO.builder()
                                        .id(x.getId())
                                        .itemId(x.getItemId())
                                        .itemName(x.getItemName())
                                        .itemImageUrl(x.getItemImageUrl())
                                        .itemDescriptionUrl(x.getItemDescriptionUrl())
                                        .originalPrice(x.getOriginalPrice())
                                        .salePrice(x.getSalePrice())
//                                        .relatedProductInfoVOList(mapRelatedItemInfo(x.getResultProductInfos()))
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public ProductQueryVOList findByInIdList(List<Long> targetIdList) {
        return ProductQueryVOList.builder()
                .productQueryVOList(
                        productJpaRepository.findByItemIdInAndIsDeletedJoinRelationship(targetIdList, false).stream()
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
                .ifPresent(product -> { throw new RuntimeException("Product with the specified item ID already exists.");}); //todo 예외처리 변경
        return ProductQueryVO.of(productJpaRepository.save(ProductCommandVO.toEntity(productCommandVO)));
    }

    @Override
    public void delete(List<Long> itemIdList) {
        productJpaRepository.findByItemIdInAndIsDeleted(itemIdList, false).forEach(ProductEntity::delete);
    }

    @Override
    public void update(ProductCommandVOList voList) {
//        // 개별 쿼리 버전
//        Map<ProductCommandVO, ProductEntity> findResults = voList.getProductCommandVOList().stream()
//                .collect(Collectors.toMap(
//                        vo -> vo,
//                        vo -> productJpaRepository.findByItemIdAndIsDeleted(vo.getItemId(), false)
//                                .orElseThrow(() -> new RuntimeException("해당 item이 없습니다."))
//                        )
//                );
//
//        findResults.forEach((vo, entity) -> entity.update(vo));

        // 한방 쿼리 버전
        List<ProductEntity> productEntityList = productJpaRepository.findByItemIdInAndIsDeleted(
                voList.getProductCommandVOList().stream()
                        .map(ProductCommandVO::getItemId)
                        .collect(Collectors.toList()),
                false);

        Map<Long, ProductEntity> productEntityMap = productEntityList.stream()
                .collect(Collectors.toMap(ProductEntity::getItemId, Function.identity()));

        voList.getProductCommandVOList().stream()
                .filter(vo -> !productEntityMap.containsKey(vo.getItemId()))
                .findFirst()
                .ifPresent(vo -> {throw new RuntimeException(String.format("해당 품목이 존재하지 않습니다. {}", vo.getItemId()));});

        voList.getProductCommandVOList().forEach(vo -> productEntityMap.get(vo.getItemId()).update(vo));
    }

    private RelatedProductInfoVOList mapRelatedItemInfo(List<ProductRelationshipEntity> entities) {
        log.debug("mapping related Product");
        return RelatedProductInfoVOList.builder()
                .voList(entities.stream()
                        .map(entity -> RelatedProductInfoVO.builder()
                                .itemId(entity.getResultProduct().getItemId())
                                .itemName(entity.getResultProduct().getItemName())
                                .itemImageUrl(entity.getResultProduct().getItemImageUrl())
                                .itemDescriptionUrl(entity.getResultProduct().getItemDescriptionUrl())
                                .originalPrice(entity.getResultProduct().getOriginalPrice())
                                .salePrice(entity.getResultProduct().getSalePrice())
                                .score(entity.getScore())
                                .rank(entity.getRank())
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
