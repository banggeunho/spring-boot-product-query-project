package com.example.techlabs.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommandRequestDTO {
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
}
