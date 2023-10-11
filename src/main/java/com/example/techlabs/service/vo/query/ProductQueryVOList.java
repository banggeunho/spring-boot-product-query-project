package com.example.techlabs.service.vo.query;

import com.example.techlabs.repository.entity.ProductEntity;
import com.example.techlabs.service.vo.command.ProductCommandVOList;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProductQueryVOList {
    private List<ProductQueryVO> productQueryVOList;

    public ProductQueryVO getByTargetItemId(Long itemId) {
        return this.productQueryVOList.stream()
                .filter(x -> x.getItemId().equals(itemId))
                .findFirst()
                .orElse(ProductQueryVO.builder().build()); //todo null 예외처리
    }

    public ProductQueryVO getByResultItemId(Long itemId) {
        return this.productQueryVOList.stream()
                .filter(x -> x.getItemId().equals(itemId))
                .findFirst()
                .orElse(ProductQueryVO.builder().build()); //todo null 예외처리
    }

    public boolean isExistByItemId(Long itemId) {
        return this.productQueryVOList.stream()
                .anyMatch(x -> x.getItemId().equals(itemId));
    }

    public static ProductQueryVOList of(List<ProductEntity> productEntityList) {
        return ProductQueryVOList.builder()
                .productQueryVOList(productEntityList.stream()
                        .filter(product -> !product.getIsDeleted())
                        .map(entity -> ProductQueryVO.builder()
                                .itemId(entity.getItemId())
                                .itemName(entity.getItemName())
                                .itemImageUrl(entity.getItemImageUrl())
                                .itemDescriptionUrl(entity.getItemDescriptionUrl())
                                .originalPrice(entity.getOriginalPrice())
                                .salePrice(entity.getSalePrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public static ProductQueryVOList of(ProductCommandVOList productCommandVOList) {
        return ProductQueryVOList.builder()
                .productQueryVOList(productCommandVOList.getProductCommandVOList().stream()
                        .map(vo -> ProductQueryVO.builder()
                                .itemId(vo.getItemId())
                                .itemName(vo.getItemName())
                                .itemImageUrl(vo.getItemImageUrl())
                                .itemDescriptionUrl(vo.getItemDescriptionUrl())
                                .originalPrice(vo.getOriginalPrice())
                                .salePrice(vo.getSalePrice())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
