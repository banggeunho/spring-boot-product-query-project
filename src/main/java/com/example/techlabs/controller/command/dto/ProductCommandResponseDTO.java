package com.example.techlabs.controller.command.dto;

import com.example.techlabs.service.vo.query.ProductQueryVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductCommandResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("item_id")
    private Long itemId;

    @JsonProperty("item_name")
    private String itemName;

    @JsonProperty("item_image")
    private String itemImageUrl;

    @JsonProperty("item_url")
    private String itemDescriptionUrl;

    @JsonProperty("original_price")
    private BigDecimal originalPrice;

    @JsonProperty("sale_price")
    private BigDecimal salePrice;

    public static ProductCommandResponseDTO of(ProductQueryVO productQueryVO) {
        return ProductCommandResponseDTO.builder()
                .id(productQueryVO.getId())
                .itemId(productQueryVO.getItemId())
                .itemName(productQueryVO.getItemName())
                .itemImageUrl(productQueryVO.getItemImageUrl())
                .itemDescriptionUrl(productQueryVO.getItemDescriptionUrl())
                .originalPrice(productQueryVO.getOriginalPrice())
                .salePrice(productQueryVO.getSalePrice())
                .build();
    }
}
