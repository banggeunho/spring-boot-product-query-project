package com.example.techlabs;

import com.example.techlabs.csv.CsvDataMigrationService;
import com.example.techlabs.csv.ProductCsvBean;
import com.example.techlabs.csv.ProductRelationshipCsvBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@EnableJpaAuditing
@SpringBootApplication
@RequiredArgsConstructor
public class TechlabsApplication {

    private final CsvDataMigrationService csvDataMigrationService;
    public static void main(String[] args) {
        SpringApplication.run(TechlabsApplication.class, args);
    }

    @PostConstruct
    public void loadDataFromCsv() throws IOException {
        //todo 로깅 설정하기..
        log.debug("start loading data.. ..");
        System.out.println("start loading date");
        List<ProductCsvBean> productCsvBeans = csvDataMigrationService.loadDataFromCsv(
                getResourcePath("product.csv"),
                ProductCsvBean.class);

        List<ProductRelationshipCsvBean> productRelationshipCsvBeans = csvDataMigrationService.loadDataFromCsv(
                getResourcePath("rec.csv"),
                ProductRelationshipCsvBean.class);

        log.debug(productCsvBeans.toString());
        log.debug(productRelationshipCsvBeans.toString());

    }

    private Path getResourcePath(String resourceName) throws IOException {
        Resource resource = new ClassPathResource("data/" + resourceName);
        if (resource.exists()) {
            try (InputStream inputStream = resource.getInputStream()) {
                return Paths.get(resource.getURI());
            }
        } else {
            throw new IOException("Resource not found: " + resourceName);
        }
    }
}
