package com.example.techlabs.service.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
public class RelatedProductInfoVO {
    private Long itemId;
    private String itemName;
    private String itemImageUrl;
    private String itemDescriptionUrl;
    private BigDecimal originalPrice;
    private BigDecimal salePrice;
    private BigDecimal score;
    private Long rank;
}
