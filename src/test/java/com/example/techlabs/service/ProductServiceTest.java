package com.example.techlabs.service;

import com.example.techlabs.service.vo.query.ProductQueryVO;
import com.example.techlabs.service.vo.query.ProductQueryVOList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceTest {

    @Autowired private ProductService productService;

    @Test
    void saveAll() {
    }

    @Test
    void 현재_DB에_저장된_모든_상품의_정보를_확인_할_수_있다() {
        ProductQueryVOList productQueryVOList = productService.findAll();
        Assertions.assertThat(productQueryVOList.getProductQueryVOList()).isNotEmpty();
        Assertions.assertThat(productQueryVOList.getProductQueryVOList().size()).isEqualTo(22);
    }

    @Test
    void 요청된_ID_LIST_를_통해_상품을_조회_할_수_있다() {
        ProductQueryVOList voList = productService.findByInIdList(List.of(300002285L, 300002301L));
        Assertions.assertThat(voList.getProductQueryVOList().size()).isEqualTo(2);
    }

    @Test
    void 요청된_ID_LIST_를_통해_상품을_조회_할때_잘못된_ID면_예외가_발생한다() {
        Assertions.assertThatThrownBy(() -> productService.findByInIdList(List.of(3243422285L)))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("존재하지 않습니다.");
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}