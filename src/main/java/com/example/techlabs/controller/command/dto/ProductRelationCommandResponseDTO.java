package com.example.techlabs.controller.command.dto;

import com.example.techlabs.service.vo.command.ProductRelationCommandVO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRelationCommandResponseDTO {
    @JsonProperty("target_item_id")
    private Long targetItemId;

    @JsonProperty("result_item_id")
    private Long resultItemId;

    @JsonProperty("score")
    private BigDecimal score;

    @JsonProperty("rank")
    private Long rank;

    public static ProductRelationCommandResponseDTO of(ProductRelationCommandVO vo) {
        return ProductRelationCommandResponseDTO.builder()
                .targetItemId(vo.getTargetItemId())
                .resultItemId(vo.getResultItemId())
                .score(vo.getScore())
                .rank(vo.getRank())
                .build();
    }

}
