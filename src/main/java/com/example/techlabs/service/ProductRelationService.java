package com.example.techlabs.service;

import com.example.techlabs.base.csv.ProductRelationshipCsvBean;
import com.example.techlabs.controller.command.dto.ProductRelationCommandRequestDTO;
import com.example.techlabs.service.vo.command.ProductRelationCommandVO;
import com.example.techlabs.service.vo.query.ProductQueryVOList;

import java.util.List;

public interface ProductRelationService {
    int saveAll(List<ProductRelationshipCsvBean> productRelationshipCsvBeans, ProductQueryVOList productVOS);

    ProductRelationCommandVO save(ProductRelationCommandVO vo);
}
