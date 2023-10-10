package com.example.techlabs.repository;

import com.example.techlabs.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
//    @Query("SELECT DISTINCT e " +
//            "FROM ProductEntity e " +
//            "JOIN FETCH e.relatedProducts " +
//            "WHERE e.itemId IN :itemIds AND e.isDeleted = :isDeleted")
    @Query("SELECT DISTINCT e " +
            "FROM ProductEntity e " +
            "JOIN FETCH e.relatedProducts r " +
            "WHERE e.itemId IN :itemIds " +
            "AND e.isDeleted = :isDeleted " +
            "AND r.isDeleted = false")
//    @EntityGraph(value = "product-entity-graph", type = EntityGraph.EntityGraphType.FETCH)
    List<ProductEntity> findByItemIdInAndIsDeletedJoinRelationship(@Param("itemIds") List<Long> itemIds, @Param("isDeleted") boolean isDeleted);

    @Query("SELECT e " +
            "FROM ProductEntity e " +
            "WHERE e.itemId IN :itemIds AND e.isDeleted = :isDeleted")
    List<ProductEntity> findByItemIdInAndIsDeleted(@Param("itemIds") List<Long> itemIds, @Param("isDeleted") boolean isDeleted);

    @Query("SELECT DISTINCT e " +
            "FROM ProductEntity e " +
            "JOIN FETCH e.relatedProducts " +
            "WHERE e.itemId = :itemId AND e.isDeleted = :isDeleted")
    Optional<ProductEntity> findByItemIdAndIsDeletedWithJoin(@Param("itemId") Long itemId, @Param("isDeleted") boolean isDeleted);

    @Modifying
    @Query("UPDATE ProductEntity p SET " +
            "p.isDeleted = true, " +
            "p.lastModifiedAt = NOW()" +
            "WHERE p.itemId IN :itemIdList " +
            "AND p.isDeleted = false")
    void bulkUpdateIsDeleted(@Param("itemIdList") List<Long> itemIdList);

    @Modifying
    @Query("UPDATE ProductEntity p SET " +
            "p.isDeleted = true " +
            "WHERE p.itemId IN :itemIdList " +
            "AND p.isDeleted = false")
    void bulkUpdate(@Param("itemIdList") List<Long> itemIdList);

    Optional<ProductEntity> findByItemId(Long itemId);

    Optional<ProductEntity> findByItemIdAndIsDeleted(Long itemId, boolean isDeleted);
}
