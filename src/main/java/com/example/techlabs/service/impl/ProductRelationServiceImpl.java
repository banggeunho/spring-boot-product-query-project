package com.example.techlabs.service.impl;

import com.example.techlabs.csv.ProductRelationshipCsvBean;
import com.example.techlabs.entity.ProductRelationshipEntity;
import com.example.techlabs.repository.ProductRelationshipJdbcRepository;
import com.example.techlabs.service.ProductRelationService;
import com.example.techlabs.service.vo.ProductQueryVOList;
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
    public int saveAll(List<ProductRelationshipCsvBean> productRelationshipCsvBeans, ProductQueryVOList productQueryVOList) {
        return productRelationshipJdbcRepository.saveAll(
                productRelationshipCsvBeans.stream()
                        .filter(x -> productQueryVOList.isExistByItemId(x.getTargetItemId()))
                        .map(x -> ProductRelationshipEntity.builder()
                                .product(productQueryVOList.getByItemId(x.getTargetItemId()).toEntity())
                                .resultItemId(x.getResultItemId())
                                .score(x.getScore())
                                .rank(x.getRank())
                                .isDel(false)
                                .createdBy("SYSTEM")
                                .lastModifiedBy("SYSTEM")
                                .build())
                        .collect(Collectors.toList())
        );
    }
}
