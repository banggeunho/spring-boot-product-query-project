package com.example.techlabs.service.impl;

import com.example.techlabs.csv.CsvBean;
import com.example.techlabs.service.LoadDataFromCSVService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LoadDataFromCSVServiceImpl implements LoadDataFromCSVService {
    @Override
    public <T extends CsvBean> List<T> loadData(Path path, Class<T> clazz) {

        List<T> results = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(path)) {
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
