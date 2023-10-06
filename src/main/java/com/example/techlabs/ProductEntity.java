package com.example.techlabs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "item_image_url", nullable = false)
    private String itemImageUrl;

    @Column(name = "item_description_url", nullable = false)
    private String itemDescriptionUrl;

    @Column(name = "original_price", nullable = false)
    private BigDecimal originalPrice;

    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;
}
