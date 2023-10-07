//package com.example.techlabs.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.experimental.SuperBuilder;
//import org.hibernate.annotations.Comment;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//
//
//@Getter
//@Setter
//@SuperBuilder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity(name = "product_histories")
//public class ProductHistoryEntity extends BaseInsertEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    private Long id;
//
//    @Comment("상품 고유코드")
//    private ProductEntity product;
//
//    @Comment("상품명")
//    @Column(name = "item_name", nullable = false)
//    private String itemName;
//
//    @Comment("상품 이미지 URL")
//    @Column(name = "item_image_url", nullable = false)
//    private String itemImageUrl;
//
//    @Comment("상품 설명 URL")
//    @Column(name = "item_description_url", nullable = false)
//    private String itemDescriptionUrl;
//
//    @Comment("상품 원가격")
//    @Column(name = "original_price", nullable = false)
//    private BigDecimal originalPrice;
//
//    @Comment("상품 판매가격")
//    @Column(name = "sale_price", nullable = false)
//    private BigDecimal salePrice;
//}
