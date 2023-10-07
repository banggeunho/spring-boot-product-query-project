package com.example.techlabs.service;

import com.example.techlabs.csv.ProductCsvBean;
import com.example.techlabs.service.vo.ProductCommandVOList;
import com.example.techlabs.service.vo.ProductQueryVO;
import com.example.techlabs.service.vo.ProductQueryVOList;

import java.util.List;

public interface ProductService {
    int saveAll(List<ProductCsvBean> productCsvBeans);

    ProductQueryVOList findAll();

    ProductQueryVOList findByInIdList(List<Long> targetIdList);
}
