package com.example.techlabs.service;

import com.example.techlabs.base.csv.ProductCsvBean;
import com.example.techlabs.service.vo.command.ProductCommandVOList;
import com.example.techlabs.service.vo.query.ProductQueryVOList;

import java.util.List;

public interface ProductService {
    void saveAll(List<ProductCsvBean> productCsvBeans);
    ProductQueryVOList findAll();
    ProductQueryVOList findByInIdList(List<Long> targetIdList);
    ProductQueryVOList save(ProductCommandVOList productCommandVO);
    void delete(List<Long> itemIdList);
    void update(ProductCommandVOList productCommandVO);
}
