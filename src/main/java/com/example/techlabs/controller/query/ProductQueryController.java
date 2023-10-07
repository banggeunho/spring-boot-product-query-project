package com.example.techlabs.controller.query;

import com.example.techlabs.controller.query.dto.ProductQueryResponseDTOList;
import com.example.techlabs.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductQueryController {

    private final ProductService productService;
    @GetMapping("")
    public ResponseEntity<ProductQueryResponseDTOList> findByItemId(@RequestParam(value = "id", required = false) List<Long> targetIdList) {
        if (targetIdList == null) {
            return ResponseEntity.ok(ProductQueryResponseDTOList.from(productService.findAll()));
        } else {
            return ResponseEntity.ok(ProductQueryResponseDTOList.from(productService.findByInIdList(targetIdList)));
        }
    }
}
