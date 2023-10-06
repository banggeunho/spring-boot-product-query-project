package com.example.techlabs.csv;

import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public interface CsvDataMigrationService {
    <T extends CsvBean> List<T> loadDataFromCsv(Path path, Class<T> clazz);
}
