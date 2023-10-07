package com.example.techlabs.controller.dto;

import com.example.techlabs.service.vo.ProductQueryVOList;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
public class ProductAndRelatedQueryResponseDTOList {
    @JsonProperty("target")
    private List<ProductQueryResponseDTO> productQueryResponseDTOList;
    @JsonProperty("size")
    private int size;

    public static ProductAndRelatedQueryResponseDTOList from(ProductQueryVOList voList) {
        return ProductAndRelatedQueryResponseDTOList.builder()
                .productQueryResponseDTOList(
                        voList.getProductQueryVOList().stream()
                                .map(vo -> ProductQueryResponseDTO.builder()
                                        .itemId(vo.getItemId())
                                        .itemName(vo.getItemName())
                                        .itemImageUrl(vo.getItemImageUrl())
                                        .itemDescriptionUrl(vo.getItemDescriptionUrl())
                                        .originalPrice(vo.getOriginalPrice())
                                        .salePrice(vo.getSalePrice())
                                        .build())
                                .collect(Collectors.toList()))
                .size(voList.getProductQueryVOList().size())
                .build();
    }
}
