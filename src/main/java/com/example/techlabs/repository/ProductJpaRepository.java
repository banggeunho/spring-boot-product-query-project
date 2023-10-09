package com.example.techlabs.repository;

import com.example.techlabs.repository.entity.ProductEntity;
import com.example.techlabs.repository.entity.ProductRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT DISTINCT e " +
            "FROM ProductEntity e " +
            "JOIN FETCH e.relatedProducts " +
            "WHERE e.itemId IN :itemIds AND e.isDeleted = :isDel")
//    @EntityGraph(value = "product-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<ProductEntity> findByItemIdInAndIsDeletedJoinRelationship(@Param("itemIds") List<Long> itemIds, @Param("isDel") boolean isDel);

    @Query("SELECT e " +
            "FROM ProductEntity e " +
            "WHERE e.itemId IN :itemIds AND e.isDeleted = :isDel")
    List<ProductEntity> findByItemIdInAndIsDeleted(@Param("itemIds") List<Long> itemIds, @Param("isDel") boolean isDel);

    @Query("SELECT DISTINCT rp.resultProduct FROM ProductRelationshipEntity rp WHERE rp.targetProduct IN :products")
    List<ProductEntity> findResultProductsForRelatedProducts(@Param("products") List<ProductEntity> products);

//    @Query("SELECT DISTINCT pe.resultProduct FROM ProductEntity pe WHERE pe IN :products")
//    List<ProductEntity> findResultProductsForRelatedProducts(@Param("products") List<ProductEntity> products);

    Optional<ProductEntity> findByItemId(Long itemId);
}
