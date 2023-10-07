package com.example.techlabs.service;

import com.example.techlabs.base.csv.ProductCsvBean;
import com.example.techlabs.service.vo.command.ProductCommandVO;
import com.example.techlabs.service.vo.query.ProductQueryVO;
import com.example.techlabs.service.vo.query.ProductQueryVOList;

import java.util.List;

public interface ProductService {
    int saveAll(List<ProductCsvBean> productCsvBeans);
    ProductQueryVOList findAll();
    ProductQueryVOList findByInIdList(List<Long> targetIdList);
    ProductQueryVO save(ProductCommandVO productCommandVO);
}
