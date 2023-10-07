package com.example.techlabs.repository;

import com.example.techlabs.entity.ProductRelationshipEntity;
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
public class ProductRelationshipJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public int saveAll(List<ProductRelationshipEntity> productRelationshipEntityList) {

        insertProductRelationships(productRelationshipEntityList);

        return getInsertedDataSize();
    }

    private void insertProductRelationships(List<ProductRelationshipEntity> productRelationshipEntityList) {

//        log.debug(productRelationshipEntityList.toString());
        log.debug("Inserted list size : {}", productRelationshipEntityList.size());

        String sql = "INSERT INTO PRODUCT_RELATIONSHIPS (" +
                "TARGET_ITEM_ID, RESULT_ITEM_ID, SCORE, RANK," +
                " CREATED_AT, CREATED_BY, LAST_MODIFIED_AT, LAST_MODIFIED_BY, IS_DEL) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ProductRelationshipEntity productRelationship = productRelationshipEntityList.get(i);
                        ps.setLong(1, productRelationship.getProduct().getItemId());
                        ps.setLong(2, productRelationship.getResultItemId());
                        ps.setBigDecimal(3, productRelationship.getScore());
                        ps.setLong(4, productRelationship.getRank());
                        ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                        ps.setString(6, productRelationship.getCreatedBy());
                        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                        ps.setString(8, productRelationship.getLastModifiedBy());
                        ps.setBoolean(9, productRelationship.getIsDel());
                    }
                    @Override
                    public int getBatchSize() {
                        return productRelationshipEntityList.size();
                    }
                }
        );
    }

    private int getInsertedDataSize() {
        String sql = "SELECT * FROM PRODUCT_RELATIONSHIPS";
        List<Long> idList = new ArrayList<>();
        jdbcTemplate.query(sql, (rs, rowNum) -> idList.add(rs.getLong("ID")));
        return idList.size();
    }
}
