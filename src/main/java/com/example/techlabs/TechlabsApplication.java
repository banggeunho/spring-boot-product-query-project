package com.example.techlabs;

import com.example.techlabs.csv.ProductCsvBean;
import com.example.techlabs.csv.ProductRelationshipCsvBean;
import com.example.techlabs.service.LoadDataFromCSVService;
import com.example.techlabs.service.ProductRelationService;
import com.example.techlabs.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class TechlabsApplication {

    private static final String CSV_ROOT_DIR = "data/";
    private static final String PRODUCT_CSV_NAME = "product.csv";
    private static final String REC_CSV_NAME = "rec.csv";
    private final ProductService productService;
    private final ProductRelationService productRelationService;
    private final LoadDataFromCSVService loadDataFromCSVService;

    public static void main(String[] args) {
        SpringApplication.run(TechlabsApplication.class, args);
    }

    @PostConstruct
    public void loadDataFromCsv() throws IOException {
        List<ProductCsvBean> productCsvBeans = loadDataFromCSVService.loadData(
                getResourcePath(PRODUCT_CSV_NAME),
                ProductCsvBean.class);

        List<ProductRelationshipCsvBean> productRelationshipCsvBeans = loadDataFromCSVService.loadData(
                getResourcePath(REC_CSV_NAME),
                ProductRelationshipCsvBean.class);

        log.debug(productCsvBeans.toString());
        log.debug(productRelationshipCsvBeans.toString());

        productService.saveAll(productCsvBeans);
        productRelationService.saveAll(productRelationshipCsvBeans, productService.findAll());

    }

    private Path getResourcePath(String resourceName) throws IOException {
        Resource resource = new ClassPathResource(CSV_ROOT_DIR + resourceName);
        if (resource.exists()) {
            try (InputStream inputStream = resource.getInputStream()) {
                return Paths.get(resource.getURI());
            }
        } else {
            throw new IOException("Resource not found: " + resourceName);
        }
    }
}
