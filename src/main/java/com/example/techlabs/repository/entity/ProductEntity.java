package com.example.techlabs.repository.entity;

import com.example.techlabs.service.vo.command.ProductCommandVO;
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

    @ToString.Exclude
    @Builder.Default
    @NotAudited
    @OneToMany(mappedBy = "targetProduct", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
//    @Where(clause = "is_deleted = false")
    private List<ProductRelationshipEntity> relatedProducts = new ArrayList<>();

//    @ToString.Exclude
//    @Builder.Default
//    @NotAudited
//    @OneToMany(mappedBy = "resultProduct", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
//    @Where(clause = "is_deleted = false")
//    private List<ProductRelationshipEntity> resultProducts = new ArrayList<>();

    public void update(ProductCommandVO productCommandVO) {
        this.setItemId(productCommandVO.getItemId());
        this.setItemName(productCommandVO.getItemName());
        this.setItemImageUrl(productCommandVO.getItemImageUrl());
        this.setItemDescriptionUrl(productCommandVO.getItemDescriptionUrl());
        this.setOriginalPrice(productCommandVO.getOriginalPrice());
        this.setSalePrice(productCommandVO.getSalePrice());
        super.onUpdate();

    }

    public void delete() {
        this.getRelatedProducts().forEach(x -> x.setIsDeleted(true));
//        this.getResultProducts().forEach(x -> x.setIsDeleted(true)); //todo 추가
        super.onDelete();
    }

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

    public static ProductEntity of(ProductCommandVO vo) {
        return ProductEntity.builder()
                .id(vo.getId() != null ? vo.getId() : null)
                .itemId(vo.getItemId())
                .itemName(vo.getItemName())
                .itemImageUrl(vo.getItemImageUrl())
                .itemDescriptionUrl(vo.getItemDescriptionUrl())
                .originalPrice(vo.getOriginalPrice())
                .salePrice(vo.getSalePrice())
                .isDeleted(false)
                .build();
    }
}
