package com.example.techlabs.service.impl;

import com.example.techlabs.base.csv.CsvBean;
import com.example.techlabs.service.LoadDataFromCSVService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LoadDataFromCSVServiceImpl implements LoadDataFromCSVService {
    @Override
    public <T extends CsvBean> List<T> loadData(String resourcePath, Class<T> clazz) {

        List<T> results = new ArrayList<>();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             Reader reader = new InputStreamReader(inputStream)) {

            CsvToBean<T> csvBean = new CsvToBeanBuilder<T>(reader)
                    .withType(clazz)
                    .build();

            results = csvBean.parse();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
