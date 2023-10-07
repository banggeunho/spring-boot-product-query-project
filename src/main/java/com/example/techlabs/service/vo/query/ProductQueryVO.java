package com.example.techlabs.service.vo.query;

import com.example.techlabs.repository.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductQueryVO {
    private Long id;
    private Long itemId;
    private String itemName;
    private String itemImageUrl;
    private String itemDescriptionUrl;
    private BigDecimal originalPrice;
    private BigDecimal salePrice;
    private RelatedProductInfoVOList relatedProductInfoVOList;

    public ProductEntity toEntity() {
        return ProductEntity.builder()
                .id(this.getId())
                .itemId(this.getItemId())
                .itemName(this.getItemName())
                .itemImageUrl(this.getItemImageUrl())
                .itemDescriptionUrl(this.getItemDescriptionUrl())
                .originalPrice(this.getOriginalPrice())
                .salePrice(this.getSalePrice())
                .build();
    }

    public static ProductQueryVO of(ProductEntity productEntity) {
        return ProductQueryVO.builder()
                .id(productEntity.getId())
                .itemId(productEntity.getItemId())
                .itemName(productEntity.getItemName())
                .itemImageUrl(productEntity.getItemImageUrl())
                .itemDescriptionUrl(productEntity.getItemDescriptionUrl())
                .originalPrice(productEntity.getOriginalPrice())
                .salePrice(productEntity.getSalePrice())
                .build();
    }
}
