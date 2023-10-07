package com.example.techlabs.controller.dto;

import com.example.techlabs.service.vo.ProductVOList;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
public class ProductResponseDTOList {
    @JsonProperty("target")
    private List<ProductResponseDTO> productResponseDTOList;

    public static ProductResponseDTOList from(ProductVOList voList) {
        return ProductResponseDTOList.builder()
                .productResponseDTOList(
                        voList.getProductVOList().stream()
                                .map(vo -> ProductResponseDTO.builder()
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
