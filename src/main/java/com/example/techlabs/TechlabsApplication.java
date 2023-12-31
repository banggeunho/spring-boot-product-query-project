package com.example.techlabs;

import com.example.techlabs.base.csv.ProductCsvBean;
import com.example.techlabs.base.csv.ProductRelationshipCsvBean;
import com.example.techlabs.service.LoadDataFromCSVService;
import com.example.techlabs.service.ProductRelationService;
import com.example.techlabs.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
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
                CSV_ROOT_DIR + PRODUCT_CSV_NAME,
                ProductCsvBean.class);

        List<ProductRelationshipCsvBean> productRelationshipCsvBeans = loadDataFromCSVService.loadData(
                CSV_ROOT_DIR + REC_CSV_NAME,
                ProductRelationshipCsvBean.class);

        productService.saveAll(productCsvBeans);
        productRelationService.saveAll(productRelationshipCsvBeans, productService.findAll());

    }
}
