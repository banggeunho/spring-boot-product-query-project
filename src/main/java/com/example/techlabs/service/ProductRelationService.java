package com.example.techlabs.service;

import com.example.techlabs.csv.ProductCsvBean;
import com.example.techlabs.csv.ProductRelationshipCsvBean;

import java.util.List;

public interface ProductRelationService {
    int saveAll(List<ProductRelationshipCsvBean> productRelationshipCsvBeans, ProductVOList productVOS);
}
