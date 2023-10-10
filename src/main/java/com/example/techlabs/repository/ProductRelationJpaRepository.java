package com.example.techlabs.repository;

import com.example.techlabs.repository.entity.ProductEntity;
import com.example.techlabs.repository.entity.ProductRelationshipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRelationJpaRepository extends JpaRepository<ProductRelationshipEntity, Long> {

    Optional<ProductRelationshipEntity> findByTargetProductAndResultItemIdAndIsDeleted(ProductEntity targetProduct,
                                                                                        Long resultItemId,
                                                                                        boolean isDeleted);

    List<ProductRelationshipEntity> findByTargetProductAndIsDeleted(ProductEntity targetProduct, boolean isDeleted);

    List<ProductRelationshipEntity> findByResultItemIdInAndIsDeleted(List<Long> resultItemId, boolean isDeleted);

    @Modifying
    @Query("UPDATE ProductRelationshipEntity p " +
            "SET p.isDeleted = true " +
            "WHERE ((p.targetProduct.itemId IN :itemIdList) OR (p.resultItemId IN :itemIdList)) " +
            "AND p.isDeleted = false")
    void bulkUpdateIsDeleted(@Param("itemIdList") List<Long> itemIdList);

//    Optional

}
