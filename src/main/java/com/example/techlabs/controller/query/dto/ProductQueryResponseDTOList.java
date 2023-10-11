package com.example.techlabs.controller.query.dto;

import com.example.techlabs.service.vo.query.ProductQueryVO;
import com.example.techlabs.service.vo.query.ProductQueryVOList;
import com.example.techlabs.service.vo.query.RelatedProductInfoVOList;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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
                                        .relatedItemSize(getSafetyRelatedProductInfoVOList(vo).getVoList().size())
                                        .relatedProductQueryResponseDTOList(getSafetyRelatedProductInfoVOList(vo).getVoList().stream()
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

    private static RelatedProductInfoVOList getSafetyRelatedProductInfoVOList(ProductQueryVO vo) {
        if (vo.getRelatedProductInfoVOList() != null && !CollectionUtils.isEmpty(vo.getRelatedProductInfoVOList().getVoList())) {
            return vo.getRelatedProductInfoVOList();
        } else {
            return RelatedProductInfoVOList.builder()
                    .voList(Collections.emptyList())
                    .build();
        }
    }
}
