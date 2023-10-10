package com.example.techlabs.service.vo.command;

import com.example.techlabs.controller.command.dto.ProductCommandRequestDTO;
import com.example.techlabs.repository.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProductCommandVO {
    private Long id;
    private Long itemId;
    private String itemName;
    private String itemImageUrl;
    private String itemDescriptionUrl;
    private BigDecimal originalPrice;
    private BigDecimal salePrice;

    public static ProductCommandVO of(ProductCommandRequestDTO requestDTO) {
        return ProductCommandVO.builder()
                .itemId(requestDTO.getItemId())
                .itemName(requestDTO.getItemName())
                .itemImageUrl(requestDTO.getItemImageUrl())
                .itemDescriptionUrl(requestDTO.getItemDescriptionUrl())
                .originalPrice(requestDTO.getOriginalPrice())
                .salePrice(requestDTO.getSalePrice())
                .build();
    }

    public static ProductEntity toEntity(ProductCommandVO vo) {
        return ProductEntity.builder()
                .id(vo.getId() != null ? vo.getId() : null)
                .itemId(vo.getItemId())
                .itemName(vo.getItemName())
                .itemImageUrl(vo.getItemImageUrl())
                .itemDescriptionUrl(vo.getItemDescriptionUrl())
                .originalPrice(vo.getOriginalPrice())
                .salePrice(vo.getSalePrice())
                .createdBy("SYSTEM")
                .lastModifiedBy("SYSTEM")
                .isDeleted(false)
                .build();
    }
}
