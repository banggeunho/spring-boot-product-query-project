package com.example.techlabs.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductCommandRequestDTO {
    @NotNull
    @JsonProperty("item_id")
    private Long itemId;

    @NotNull
    @JsonProperty("item_name")
    private String itemName;

    @NotNull
    @JsonProperty("item_image")
    private String itemImageUrl;

    @NotNull
    @JsonProperty("item_url")
    private String itemDescriptionUrl;

    @NotNull
    @JsonProperty("original_price")
    private BigDecimal originalPrice;

    @NotNull
    @JsonProperty("sale_price")
    private BigDecimal salePrice;
}
