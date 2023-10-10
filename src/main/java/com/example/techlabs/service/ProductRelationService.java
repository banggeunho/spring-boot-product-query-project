package com.example.techlabs.service;

import com.example.techlabs.base.csv.ProductRelationshipCsvBean;
import com.example.techlabs.service.vo.command.ProductRelationCommandVO;
import com.example.techlabs.service.vo.query.ProductQueryVOList;

import java.util.List;

public interface ProductRelationService {
    void saveAll(List<ProductRelationshipCsvBean> productRelationshipCsvBeans, ProductQueryVOList productVOS);

    ProductRelationCommandVO save(ProductRelationCommandVO vo);

    ProductRelationCommandVO update(ProductRelationCommandVO vo);

    void delete(Long targetItemId, Long resultItemId);
}
