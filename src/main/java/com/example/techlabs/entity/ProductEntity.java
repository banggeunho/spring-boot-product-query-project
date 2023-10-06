package com.example.techlabs.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class ProductEntity extends BaseUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("상품 고유코드")
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Comment("상품명")
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Comment("상품 이미지 URL")
    @Column(name = "item_image_url", nullable = false)
    private String itemImageUrl;

    @Comment("상품 설명 URL")
    @Column(name = "item_description_url", nullable = false)
    private String itemDescriptionUrl;

    @Comment("상품 원가격")
    @Column(name = "original_price", nullable = false)
    private BigDecimal originalPrice;

    @Comment("상품 판매가격")
    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;

    @OneToMany(mappedBy = "product")
    private List<ProductRelationshipEntity> relatedProducts;
}
