package com.example.techlabs.base.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCodeEnum {

    ALREADY_EXISTS_PRODUCT("이미 해당 아이디를 가진 상품이 존재합니다."),
    ALREADY_EXISTS_PRODUCT_RELATIONSHIP("이미 해당 상품들간의 연관관계가 존재합니다."),
    NOT_EXISTS_PRODUCT("해당 아이디를 가진 상품이 존재하지 않습니다."),
    NOT_EXISTS_TARGET_PRODUCT("[타겟 상품] 해당 아이디를 가진 상품이 존재하지 않습니다."),
    NOT_EXISTS_RESULT_PRODUCT("[연관 상품] 해당 아이디를 가진 상품이 존재하지 않습니다."),
    NOT_EXISTS_PRODUCT_RELATIONSHIP("해당 상품들간의 연관관계가 존재하지 않습니다."),
    DO_NOT_ALLOW_SAME_PRODUCT_ID("같은 상품 아이디끼리 연관관계를 만들 수 없습니다.")
    ;

    private String message;
}
