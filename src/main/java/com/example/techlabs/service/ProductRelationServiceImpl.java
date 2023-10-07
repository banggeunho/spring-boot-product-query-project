package com.example.techlabs.service;

import com.example.techlabs.csv.ProductRelationshipCsvBean;
import com.example.techlabs.entity.ProductEntity;
import com.example.techlabs.entity.ProductRelationshipEntity;
import com.example.techlabs.repository.ProductRelationshipJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRelationServiceImpl implements ProductRelationService {

    private final ProductRelationshipJdbcRepository productRelationshipJdbcRepository;
    @Override
    public int saveAll(List<ProductRelationshipCsvBean> productRelationshipCsvBeans, ProductVOList productVOS) {
        return productRelationshipJdbcRepository.saveAll(
                productRelationshipCsvBeans.stream()
                        .filter(x -> productVOS.isExistByItemId(x.getTargetItemId()))
                        .map(x -> ProductRelationshipEntity.builder()
                                .product(productVOS.getByItemId(x.getTargetItemId()).toEntity())
                                .resultItemId(x.getResultItemId())
                                .score(x.getScore())
                                .rank(x.getRank())
                                .build())
                        .collect(Collectors.toList())
        );
    }
}
