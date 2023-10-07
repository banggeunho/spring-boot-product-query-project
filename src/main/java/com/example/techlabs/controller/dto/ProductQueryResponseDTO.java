package com.example.techlabs.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@ToString
public class ProductQueryResponseDTO {
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

    @JsonProperty("related_item_size")
    private int relatedItemSize;

    @JsonProperty("results")
    private List<RelatedProductQueryResponseDTO> relatedProductQueryResponseDTOList;
}
