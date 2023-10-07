package com.example.techlabs.service.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class RelatedProductInfoVOList {
    private List<RelatedProductInfoVO> voList;
}
