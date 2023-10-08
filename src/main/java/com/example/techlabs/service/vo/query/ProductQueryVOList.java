package com.example.techlabs.service.vo.query;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductQueryVOList {
    private List<ProductQueryVO> productQueryVOList;

    public ProductQueryVO getByTargetItemId(Long itemId) {
        return this.productQueryVOList.stream()
                .filter(x -> x.getItemId().equals(itemId))
                .findFirst()
                .orElse(ProductQueryVO.builder().build()); //todo null 예외처리
    }

    public ProductQueryVO getByResultItemId(Long itemId) {
        return this.productQueryVOList.stream()
                .filter(x -> x.getItemId().equals(itemId))
                .findFirst()
                .orElse(ProductQueryVO.builder().build()); //todo null 예외처리
    }

    public boolean isExistByItemId(Long itemId) {
        return this.productQueryVOList.stream()
                .anyMatch(x -> x.getItemId().equals(itemId));
    }
}
