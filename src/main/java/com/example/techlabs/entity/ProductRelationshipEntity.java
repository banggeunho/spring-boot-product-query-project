package com.example.techlabs.entity;
import javax.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.math.BigDecimal;
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_relationships", indexes = @Index(name = "idx__target_item_id", columnList = "target_item_id"))
public class ProductRelationshipEntity extends BaseUpdateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("타겟 상품 ID")
    @ManyToOne
    @JoinColumn(name = "target_item_id", referencedColumnName = "item_id", nullable = false)
    private ProductEntity product;

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
