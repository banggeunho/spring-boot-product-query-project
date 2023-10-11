package com.example.techlabs.controller.command;

import com.example.techlabs.controller.command.dto.ProductCommandRequestDTO;
import com.example.techlabs.controller.command.dto.ProductCommandResponseDTO;
import com.example.techlabs.service.ProductService;
import com.example.techlabs.service.vo.command.ProductCommandVO;
import com.example.techlabs.service.vo.command.ProductCommandVOList;
import com.example.techlabs.service.vo.query.ProductQueryVOList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductCommandController {

    private final ProductService productService;
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductCommandResponseDTO> save(@Valid @RequestBody List<ProductCommandRequestDTO> dtoList) {
        return productService.save(
                        ProductCommandVOList.builder()
                                .productCommandVOList(
                                        dtoList.stream()
                                                .map(ProductCommandVO::of)
                                                .collect(Collectors.toList()))
                                .build())
                .getProductQueryVOList().stream()
                .map(ProductCommandResponseDTO::of)
                .collect(Collectors.toList());
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam(value = "id", required = true) List<Long> itemIdList) {
        productService.delete(itemIdList);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ProductQueryVOList update(@Valid @RequestBody List<ProductCommandRequestDTO> dtoList) {
        return productService.update(
                ProductCommandVOList.builder()
                        .productCommandVOList(
                                dtoList.stream()
                                        .map(ProductCommandVO::of)
                                        .collect(Collectors.toList()))
                        .build()
        );
    }
}

