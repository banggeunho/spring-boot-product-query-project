package com.example.techlabs.service;

import com.example.techlabs.base.common.CommonUtil;
import com.example.techlabs.service.vo.command.ProductCommandVO;
import com.example.techlabs.service.vo.command.ProductCommandVOList;
import com.example.techlabs.service.vo.query.ProductQueryVOList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceㄲTest {

    @Autowired
    private ProductService productService;

    private static MockedStatic<CommonUtil> commonUtil;
    @BeforeAll
    public static void setup() {
        commonUtil = mockStatic(CommonUtil.class);
    }

    @AfterAll
    public static void close() {
        commonUtil.close();
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
    void 다수의_상품을_등록할_수_있다() {
        ProductCommandVOList voList = ProductCommandVOList.builder()
                .productCommandVOList(List.of(
                        ProductCommandVO.builder()
                                .itemId(123L)
                                .itemName("뚜비두밥")
                                .itemImageUrl("/asdas")
                                .itemDescriptionUrl("/asadsad")
                                .originalPrice(BigDecimal.ONE)
                                .salePrice(BigDecimal.ONE)
                                .build()
                        ,ProductCommandVO.builder()
                                .itemId(323L)
                                .itemName("뚜비두밥")
                                .itemImageUrl("/asdas")
                                .itemDescriptionUrl("/asadsad")
                                .originalPrice(BigDecimal.ONE)
                                .salePrice(BigDecimal.ONE)
                                .build()
                ))
                .build();

        when(CommonUtil.extractKey(any(), any()))
                .thenReturn(List.of(123L, 323L));

        ProductQueryVOList resultList = productService.save(voList);

        Assertions.assertThat(resultList.getProductQueryVOList().size()).isEqualTo(2);
        Assertions.assertThat(resultList.isExistByItemId(123L)).isTrue();
        Assertions.assertThat(resultList.isExistByItemId(323L)).isTrue();

    }

    @Test
    void 상품_등록시_해당_상품_id가_존재하면_예외를_발생한다() {
        ProductCommandVOList voList = ProductCommandVOList.builder()
                .productCommandVOList(List.of(
                        ProductCommandVO.builder()
                                .itemId(300002285L)
                                .itemName("뚜비두밥")
                                .itemImageUrl("/asdas")
                                .itemDescriptionUrl("/asadsad")
                                .originalPrice(BigDecimal.ONE)
                                .salePrice(BigDecimal.ONE)
                                .build()
                        ,ProductCommandVO.builder()
                                .itemId(323L)
                                .itemName("뚜비두밥")
                                .itemImageUrl("/asdas")
                                .itemDescriptionUrl("/asadsad")
                                .originalPrice(BigDecimal.ONE)
                                .salePrice(BigDecimal.ONE)
                                .build()
                ))
                .build();

        when(CommonUtil.extractKey(any(), any()))
                .thenReturn(List.of(300002285L, 323L));

        Assertions.assertThatThrownBy(() -> productService.save(voList))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("이미 존재합니다.");
    }

    @Test
    void 상품_삭제시_해당_상품_id가_존재하지_않으면_예외를_발생한다() {
        List<Long> itemIdList = List.of(1L, 2L);

        Assertions.assertThatThrownBy(() -> productService.delete(itemIdList))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("않습니다.");
    }

    @Test
    void 다수의_상품_정보를_수정할_수_있습니다() {
        ProductCommandVOList voList = ProductCommandVOList.builder()
                .productCommandVOList(List.of(
                        ProductCommandVO.builder()
                                .itemId(300002285L)
                                .itemName("뚜비두밥")
                                .itemImageUrl("/asdas")
                                .itemDescriptionUrl("/asadsad")
                                .originalPrice(BigDecimal.ONE)
                                .salePrice(BigDecimal.ONE)
                                .build()
                        ,ProductCommandVO.builder()
                                .itemId(301156471L)
                                .itemName("뚜비두밥")
                                .itemImageUrl("/asdas")
                                .itemDescriptionUrl("/asadsad")
                                .originalPrice(BigDecimal.ONE)
                                .salePrice(BigDecimal.ONE)
                                .build()
                ))
                .build();

        when(CommonUtil.extractKey(any(), any()))
                .thenReturn(List.of(300002285L, 301156471L));

        when(CommonUtil.groupByKey(any(), any()))
                .thenCallRealMethod();

        ProductQueryVOList productQueryVOList = productService.update(voList);

        Assertions.assertThat(productQueryVOList.getProductQueryVOList().size()).isEqualTo(2);
        Assertions.assertThat(productQueryVOList.isExistByItemId(300002285L)).isTrue();
        Assertions.assertThat(productQueryVOList.getProductQueryVOList().get(0).getItemName()).isEqualTo("뚜비두밥");
    }

    @Test
    void update() {
    }
}