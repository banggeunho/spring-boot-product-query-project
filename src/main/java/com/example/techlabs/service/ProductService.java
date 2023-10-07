package com.example.techlabs.service;

import com.example.techlabs.csv.ProductCsvBean;
import com.example.techlabs.service.vo.ProductVOList;

import java.util.List;

public interface ProductService {
    int saveAll(List<ProductCsvBean> productCsvBeans);

    ProductVOList findAll();
}
