package com.example.techlabs.controller.query.dto;

import com.example.techlabs.service.vo.query.ProductQueryVOList;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
public class ProductQueryResponseDTOList {
    @JsonProperty("target")
    private List<ProductQueryResponseDTO> productQueryResponseDTOList;
    @JsonProperty("target_item_size")
    private int targetSize;

    public static ProductQueryResponseDTOList from(ProductQueryVOList voList) {
        return ProductQueryResponseDTOList.builder()
                .productQueryResponseDTOList(
                        voList.getProductQueryVOList().stream()
                                .map(vo -> ProductQueryResponseDTO.builder()
                                        .itemId(vo.getItemId())
                                        .itemName(vo.getItemName())
                                        .itemImageUrl(vo.getItemImageUrl())
                                        .itemDescriptionUrl(vo.getItemDescriptionUrl())
                                        .originalPrice(vo.getOriginalPrice())
                                        .salePrice(vo.getSalePrice())
                                        .relatedItemSize(vo.getRelatedProductInfoVOList().getVoList().size())
                                        .relatedProductQueryResponseDTOList(vo.getRelatedProductInfoVOList().getVoList().stream()
                                                .map(relatedProductInfoVO -> RelatedProductQueryResponseDTO.builder()
                                                        .itemId(relatedProductInfoVO.getItemId())
                                                        .itemName(relatedProductInfoVO.getItemName())
                                                        .itemImageUrl(relatedProductInfoVO.getItemImageUrl())
                                                        .itemDescriptionUrl(relatedProductInfoVO.getItemDescriptionUrl())
                                                        .originalPrice(relatedProductInfoVO.getOriginalPrice())
                                                        .salePrice(relatedProductInfoVO.getSalePrice())
                                                        .score(relatedProductInfoVO.getScore())
                                                        .rank(relatedProductInfoVO.getRank())
                                                        .build())
                                                .collect(Collectors.toList()))
                                        .build())
                                .collect(Collectors.toList()))
                .targetSize(voList.getProductQueryVOList().size())
                .build();
    }
}
