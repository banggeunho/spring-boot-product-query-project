package com.example.techlabs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductRelationshipsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_item_id", columnDefinition = "타겟 상품 ID")
    private Long targetItemId;

    @Column(name = "result_item_id", columnDefinition = "연관된 상품 ID")
    private String resultItemId;

    @Column(name = "score", columnDefinition = "연관도 점수")
    private BigDecimal score;

    @Column(name = "original_price", columnDefinition = "연관 상품 순위")
    private BigDecimal originalPrice;

}
