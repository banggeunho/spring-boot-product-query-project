package com.example.techlabs.repository.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class ProductRelationshipId implements Serializable {
    private Long targetProduct;
    private Long resultProduct;
}
