package com.example.techlabs.csv;

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
public class CsvDataMigrationServiceImpl implements CsvDataMigrationService {
    @Override
    public <T extends CsvBean> List<T> loadDataFromCsv(Path path, Class<T> clazz) {

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
