package com.example.techlabs.service.impl;

import com.example.techlabs.csv.ProductCsvBean;
import com.example.techlabs.entity.ProductEntity;
import com.example.techlabs.entity.ProductRelationshipEntity;
import com.example.techlabs.repository.ProductJdbcRepository;
import com.example.techlabs.repository.ProductJpaRepository;
import com.example.techlabs.service.ProductService;
import com.example.techlabs.service.vo.ProductQueryVO;
import com.example.techlabs.service.vo.ProductQueryVOList;
import com.example.techlabs.service.vo.RelatedProductInfoVO;
import com.example.techlabs.service.vo.RelatedProductInfoVOList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    private RelatedProductInfoVOList mapRelatedItemInfo(List<ProductRelationshipEntity> productRelationshipEntities) {
        return RelatedProductInfoVOList.builder()
                .voList(productRelationshipEntities.stream()
                        .map(productRelationshipEntity -> {
                            ProductEntity resultProduct = productJpaRepository.findByItemId(
                                    productRelationshipEntity.getResultItemId()).orElse(null);

                            if (resultProduct == null) {
                                return null;
                            }

                            return RelatedProductInfoVO.builder()
                                    .itemId(resultProduct.getItemId())
                                    .itemName(resultProduct.getItemName())
                                    .itemImageUrl(resultProduct.getItemImageUrl())
                                    .itemDescriptionUrl(resultProduct.getItemDescriptionUrl())
                                    .originalPrice(resultProduct.getOriginalPrice())
                                    .salePrice(resultProduct.getSalePrice())
                                    .score(productRelationshipEntity.getScore())
                                    .rank(productRelationshipEntity.getRank())
                                    .build();
                        })
                        .collect(Collectors.toList())
                )
                .build();
    }
}
