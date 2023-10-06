package com.example.techlabs.entity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product_relationships")
public class ProductRelationshipEntity extends BaseUpdateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("타겟 상품 ID")
    @ManyToOne
    @JoinColumn(name = "target_item_id", nullable = false)
    private ProductEntity product;

    @Comment("연관된 상품 ID")
    @Column(name = "result_item_id", nullable = false)
    private String resultItemId;

    @Comment("연관도 점수")
    @Column(name = "score", nullable = false)
    private BigDecimal score;

    @Comment("연관 상품 순위")
    @Column(name = "original_price", nullable = false)
    private BigDecimal originalPrice;
}
