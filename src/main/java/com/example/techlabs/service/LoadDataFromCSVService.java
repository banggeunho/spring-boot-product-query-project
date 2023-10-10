package com.example.techlabs.service;

import com.example.techlabs.base.csv.CsvBean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoadDataFromCSVService {
    <T extends CsvBean> List<T> loadData(String resource, Class<T> clazz);
}
