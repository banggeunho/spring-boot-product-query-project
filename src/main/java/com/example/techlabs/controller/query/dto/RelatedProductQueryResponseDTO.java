package com.example.techlabs.controller.query.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
public class RelatedProductQueryResponseDTO {
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

    @JsonProperty("score")
    private BigDecimal score;

    @JsonProperty("rank")
    private Long rank;
}
