package com.example.techlabs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductRelationshipHistoriesEntity extends BaseInsertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("타겟 상품 ID")
    @Column(name = "target_item_id", nullable = false)
    private Long targetItemId;

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
