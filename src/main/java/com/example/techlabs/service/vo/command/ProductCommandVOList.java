package com.example.techlabs.service.vo.command;

import com.example.techlabs.controller.command.dto.ProductCommandRequestDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ProductCommandVOList {
    private List<ProductCommandVO> productCommandVOList;

    public static ProductCommandVOList of(List<ProductCommandRequestDTO> dtoList) {
        return ProductCommandVOList.builder()
                .productCommandVOList(
                        dtoList.stream()
                                .map(ProductCommandVO::of)
                                .collect(Collectors.toList()))
                .build();
    }
}
