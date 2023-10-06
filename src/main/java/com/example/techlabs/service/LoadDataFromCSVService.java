package com.example.techlabs.service;

import com.example.techlabs.csv.CsvBean;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public interface LoadDataFromCSVService {
    <T extends CsvBean> List<T> loadData(Path path, Class<T> clazz);
}
