package com.example.techlabs.controller.query;

import com.example.techlabs.controller.dto.ProductCommandRequestDTO;
import com.example.techlabs.controller.dto.ProductCommandResponseDTO;
import com.example.techlabs.service.ProductService;
import com.example.techlabs.service.vo.ProductCommandVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductCommandController {

    private final ProductService productService;
    @PostMapping("") //todo 검증 추가..
    public ResponseEntity<ProductCommandResponseDTO> save(@RequestBody ProductCommandRequestDTO dto) {
        return new ResponseEntity<>(
                ProductCommandResponseDTO.of(productService.save(ProductCommandVO.of(dto))),
                HttpStatus.CREATED);
    }
}

