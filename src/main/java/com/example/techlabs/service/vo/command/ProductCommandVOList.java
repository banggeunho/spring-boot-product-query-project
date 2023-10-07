package com.example.techlabs.service.vo.command;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductCommandVOList {
    private List<ProductCommandVO> productCommandVOList;
}
