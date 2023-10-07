package com.example.techlabs.service.vo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductVOList {
    private List<ProductVO> productVOList;

    public ProductVO getByItemId(Long itemId) {
        return this.productVOList.stream()
                .filter(x -> x.getItemId().equals(itemId))
                .findFirst()
                .orElse(ProductVO.builder().build()); //todo null 예외처리
    }

    public boolean isExistByItemId(Long itemId) {
        return this.productVOList.stream()
                .anyMatch(x -> x.getItemId().equals(itemId));
    }
}
