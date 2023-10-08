package com.example.techlabs.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Audited
@ToString(callSuper = true) //fixme 순환 호출
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products", indexes = @Index(name = "idx__item_id", columnList = "item_id"))
public class ProductEntity extends BaseUpdateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @Comment("상품 삭제여부")
    @Column(name = "is_del", nullable = false)
    private Boolean isDel;

    @ToString.Exclude
    @Builder.Default
    @NotAudited
    @OneToMany(mappedBy = "targetProduct", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductRelationshipEntity> relatedProducts = new ArrayList<>();

//    @ToString.Exclude
//    @Builder.Default
//    @NotAudited
//    @OneToMany(mappedBy = "resultProduct", orphanRemoval = true, cascade = CascadeType.ALL)
//    private List<ProductRelationshipEntity> resultProducts = new ArrayList<>();

    public List<ResultProductInfo> getResultProductInfos() {
        List<ResultProductInfo> resultProductIds = new ArrayList<>();
        this.relatedProducts.forEach(x -> resultProductIds.add(
                ResultProductInfo.builder()
                        .itemId(x.getResultItemId())
                        .rank(x.getRank())
                        .score(x.getScore())
                        .build()));

        return resultProductIds;
    }

    @Builder
    @Getter
    public static class ResultProductInfo {
        private BigDecimal score;
        private Long rank;
        private Long itemId;
    }
}
