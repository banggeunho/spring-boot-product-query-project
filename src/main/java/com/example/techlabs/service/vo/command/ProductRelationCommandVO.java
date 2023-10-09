package com.example.techlabs.service.vo.command;

import com.example.techlabs.controller.command.dto.ProductRelationCommandRequestDTO;
import com.example.techlabs.repository.entity.ProductRelationshipEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductRelationCommandVO {
    private Long targetItemId;
    private Long resultItemId;
    private BigDecimal score;
    private Long rank;

    public static ProductRelationCommandVO of(ProductRelationCommandRequestDTO requestDTO) {
        return ProductRelationCommandVO.builder()
                .targetItemId(requestDTO.getTargetItemId())
                .resultItemId(requestDTO.getResultItemId())
                .score(requestDTO.getScore())
                .build();
    }

//    public static ProductRelationshipEntity toEntity(ProductRelationCommandVO vo) {
//        return ProductRelationshipEntity.builder()
//                .targetProduct(vo.targetItemId.)
//                .itemName(vo.getItemName())
//                .itemImageUrl(vo.getItemImageUrl())
//                .itemDescriptionUrl(vo.getItemDescriptionUrl())
//                .originalPrice(vo.getOriginalPrice())
//                .salePrice(vo.getSalePrice())
//                .isDeleted(false)
//                .build();
//    }
}
