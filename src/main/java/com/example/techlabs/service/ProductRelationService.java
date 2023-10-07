package com.example.techlabs.service;

import com.example.techlabs.csv.ProductRelationshipCsvBean;
import com.example.techlabs.service.vo.ProductVOList;

import java.util.List;

public interface ProductRelationService {
    int saveAll(List<ProductRelationshipCsvBean> productRelationshipCsvBeans, ProductVOList productVOS);
}
