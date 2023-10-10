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
public interface ProductRelationJpaRepository extends JpaRepository<ProductRelationshipEntity, Long> {

    Optional<ProductRelationshipEntity> findByTargetProductAndResultItemIdAndIsDeleted(ProductEntity targetProduct,
                                                                                        Long resultItemId,
                                                                                        boolean isDeleted);

    List<ProductRelationshipEntity> findByTargetProductAndIsDeleted(ProductEntity targetProduct, boolean isDeleted);

//    Optional

}
