package com.example.techlabs.controller.command.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductRelationCommandRequestDTO {
    @NotNull
    @JsonProperty("target_item_id")
    private Long targetItemId;

    @NotNull
    @JsonProperty("result_item_id")
    private Long resultItemId;

    @NotNull
    @JsonProperty("score")
    private BigDecimal score;
}
