package com.example.techlabs.controller.command;

import com.example.techlabs.controller.command.dto.ProductCommandRequestDTO;
import com.example.techlabs.controller.command.dto.ProductCommandResponseDTO;
import com.example.techlabs.service.ProductService;
import com.example.techlabs.service.vo.command.ProductCommandVO;
import com.example.techlabs.service.vo.command.ProductCommandVOList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductCommandController {

    private final ProductService productService;
    @PostMapping("") //todo 검증 추가..
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCommandResponseDTO save(@RequestBody ProductCommandRequestDTO dto) {
        return ProductCommandResponseDTO.of(productService.save(ProductCommandVO.of(dto)));
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam(value = "id", required = true) List<Long> itemIdList) {
        productService.delete(itemIdList);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody List<ProductCommandRequestDTO> dtoList) {
        productService.update(
                ProductCommandVOList.builder()
                        .productCommandVOList(
                                dtoList.stream()
                                        .map(ProductCommandVO::of)
                                        .collect(Collectors.toList()))
                        .build()
        );

    }
}

