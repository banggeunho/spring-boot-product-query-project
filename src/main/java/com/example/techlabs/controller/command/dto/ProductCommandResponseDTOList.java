package com.example.techlabs.controller.command.dto;

import com.example.techlabs.service.vo.query.ProductQueryVOList;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProductCommandResponseDTOList {
    @JsonProperty("data")
    List<ProductCommandResponseDTO> dtoList;

    public static ProductCommandResponseDTOList of(ProductQueryVOList voList) {
        return ProductCommandResponseDTOList.builder()
                .dtoList(voList.getProductQueryVOList().stream()
                        .map(ProductCommandResponseDTO::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
