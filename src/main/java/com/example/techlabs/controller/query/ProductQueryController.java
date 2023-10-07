package com.example.techlabs.controller.query;

import com.example.techlabs.controller.dto.ProductResponseDTOList;
import com.example.techlabs.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<ProductResponseDTOList> findAll() {
        return ResponseEntity.ok(ProductResponseDTOList.from(productService.findAll()));
    }
}
