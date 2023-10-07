package com.example.techlabs.service;

import com.example.techlabs.csv.ProductRelationshipCsvBean;
import com.example.techlabs.service.vo.ProductQueryVOList;

import java.util.List;

public interface ProductRelationService {
    int saveAll(List<ProductRelationshipCsvBean> productRelationshipCsvBeans, ProductQueryVOList productVOS);
}
