package com.example.techlabs.service;

import com.example.techlabs.service.vo.command.ProductRelationCommandVO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRelationServiceTest {

    @Autowired
    private ProductRelationService productRelationService;

    @Test
    void 상품_연관_정보를_저장할_수_있다() {
        ProductRelationCommandVO productRelationCommandVO = ProductRelationCommandVO.builder()
                .resultItemId(300002285L)
                .targetItemId(301049180L)
                .score(BigDecimal.TEN)
                .build();

        ProductRelationCommandVO result = productRelationService.save(productRelationCommandVO);

        Assertions.assertThat(result.getResultItemId()).isEqualTo(300002285L);
        Assertions.assertThat(result.getTargetItemId()).isEqualTo(301049180L);
        Assertions.assertThat(result.getRank()).isEqualTo(1L);
    }

    @Test
    void 상품_연관_정보_저장_시_타겟_상품이_없으면_예외_발생() {
        ProductRelationCommandVO productRelationCommandVO = ProductRelationCommandVO.builder()
                .resultItemId(300002285L)
                .targetItemId(12313L)
                .score(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> productRelationService.save(productRelationCommandVO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 상품_연관_정보_저장_시_연관_상품이_없으면_예외_발생() {
        ProductRelationCommandVO productRelationCommandVO = ProductRelationCommandVO.builder()
                .resultItemId(300012313302285L)
                .targetItemId(300002285L)
                .score(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> productRelationService.save(productRelationCommandVO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 상품_연관_정보_저장_시_연관_정보가_이미_존재한다면_예외_발생() {
        ProductRelationCommandVO productRelationCommandVO = ProductRelationCommandVO.builder()
                .targetItemId(300002285L)
                .resultItemId(300373871L)
                .score(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> productRelationService.save(productRelationCommandVO))
                .isInstanceOf(EntityExistsException.class);
    }

    @Test
    void 상품_연관_정보를_수정할_수_있다() {
        ProductRelationCommandVO productRelationCommandVO = ProductRelationCommandVO.builder()
                .resultItemId(300373871L)
                .targetItemId(300002285L)
                .score(BigDecimal.valueOf(100))
                .build();

        ProductRelationCommandVO result = productRelationService.update(productRelationCommandVO);

        Assertions.assertThat(result.getResultItemId()).isEqualTo(300373871L);
        Assertions.assertThat(result.getTargetItemId()).isEqualTo(300002285L);
        Assertions.assertThat(result.getRank()).isEqualTo(1L);
    }

    @Test
    void 상품_연관_정보_수정_시_타겟_상품이_없으면_예외_발생() {
        ProductRelationCommandVO productRelationCommandVO = ProductRelationCommandVO.builder()
                .resultItemId(300002285L)
                .targetItemId(12313L)
                .score(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> productRelationService.update(productRelationCommandVO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 상품_연관_정보_수정_시_연관_상품이_없으면_예외_발생() {
        ProductRelationCommandVO productRelationCommandVO = ProductRelationCommandVO.builder()
                .resultItemId(300012313302285L)
                .targetItemId(300002285L)
                .score(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> productRelationService.update(productRelationCommandVO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 상품_연관_정보_수정_시_연관_정보가_없다면_예외_발생() {
        ProductRelationCommandVO productRelationCommandVO = ProductRelationCommandVO.builder()
                .targetItemId(300002285L)
                .resultItemId(301157258L)
                .score(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> productRelationService.update(productRelationCommandVO))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 상품_연관_정보_삭제_시_타겟_상품이_없으면_예외_발생() {
        Assertions.assertThatThrownBy(() -> productRelationService.delete(300002285L, 12313L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 상품_연관_정보_삭제_시_연관_상품이_없으면_예외_발생() {
        Assertions.assertThatThrownBy(() -> productRelationService.delete(300012313302285L, 300002285L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void 상품_연관_정보_삭제_시_연관_정보가_없다면_예외_발생() {
        ProductRelationCommandVO productRelationCommandVO = ProductRelationCommandVO.builder()
                .targetItemId(300002285L)
                .resultItemId(301157258L)
                .score(BigDecimal.TEN)
                .build();

        Assertions.assertThatThrownBy(() -> productRelationService.delete(300002285L, 301157258L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}