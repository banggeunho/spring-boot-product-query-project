package com.example.techlabs.repository;

import com.example.techlabs.repository.entity.ProductEntity;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT e FROM ProductEntity e WHERE e.itemId IN :itemIds AND e.isDel = :isDel")
    List<ProductEntity> findByItemIdInAndIsDeleted(@Param("itemIds") List<Long> idList,
                                                  @Param("isDel") @DefaultValue("false") boolean isDel);
    Optional<ProductEntity> findByItemId(Long itemId);
}
