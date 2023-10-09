package com.example.techlabs.repository;

import com.example.techlabs.repository.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public int saveAll(List<ProductEntity> productEntityList) {

        insertProducts(productEntityList);

        return getInsertedDataSize();
    }

    private void insertProducts(List<ProductEntity> productEntityList) {

        log.debug("Inserted list size : {}", productEntityList.size());

        String sql = "INSERT INTO PRODUCTS (" +
                "ITEM_ID, ITEM_IMAGE_URL, ITEM_DESCRIPTION_URL, ITEM_NAME, ORIGINAL_PRICE, SALE_PRICE," +
                " CREATED_AT, CREATED_BY, LAST_MODIFIED_AT, LAST_MODIFIED_BY, IS_DELETED) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ProductEntity product = productEntityList.get(i);
                        ps.setLong(1, product.getItemId());
                        ps.setString(2, product.getItemImageUrl());
                        ps.setString(3, product.getItemDescriptionUrl());
                        ps.setString(4, product.getItemName());
                        ps.setBigDecimal(5, product.getOriginalPrice());
                        ps.setBigDecimal(6, product.getSalePrice());
                        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                        ps.setString(8, product.getCreatedBy());
                        ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                        ps.setString(10, product.getLastModifiedBy());
                        ps.setBoolean(11, product.getIsDeleted());
                    }
                    @Override
                    public int getBatchSize() {
                        return productEntityList.size();
                    }
                }
        );
    }

    private int getInsertedDataSize() {
        String sql = "SELECT * FROM PRODUCTS";
        List<Long> idList = new ArrayList<>();
        jdbcTemplate.query(sql, (rs, rowNum) -> idList.add(rs.getLong("ID")));
        return idList.size();
    }
}
