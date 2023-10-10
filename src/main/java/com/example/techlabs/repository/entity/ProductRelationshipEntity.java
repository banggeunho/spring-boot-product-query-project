package com.example.techlabs.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_relationships", indexes = {
        @Index(name = "idx__target_item_id", columnList = "target_item_id"),
        @Index(name = "idx__target_item_id__result_item_id", columnList = "target_item_id,result_item_id")
})
//@IdClass(ProductRelationshipId.class)
public class ProductRelationshipEntity extends BaseUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @Id
    @Comment("타겟 상품 ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_item_id", referencedColumnName = "item_id", nullable = false)
    @ToString.Exclude
    private ProductEntity targetProduct;

    @Comment("연관된 상품 ID")
    @Column(name = "result_item_id", nullable = false)
    private Long resultItemId;

    @Comment("연관도 점수")
    @Column(name = "score", nullable = false)
    private BigDecimal score;

    @Comment("연관 상품 순위")
    @Column(name = "rank", nullable = false)
    private Long rank;
}
