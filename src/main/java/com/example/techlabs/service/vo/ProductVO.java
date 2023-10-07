package com.example.techlabs.service.vo;

import com.example.techlabs.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductVO {
    private Long id;
    private Long itemId;
    private String itemName;
    private String itemImageUrl;
    private String itemDescriptionUrl;
    private BigDecimal originalPrice;
    private BigDecimal salePrice;

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
}
