package com.example.techlabs.controller.command;

import com.example.techlabs.controller.command.dto.ProductCommandRequestDTO;
import com.example.techlabs.controller.command.dto.ProductCommandResponseDTOList;
import com.example.techlabs.service.ProductService;
import com.example.techlabs.service.vo.command.ProductCommandVOList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductCommandController {

    private final ProductService productService;
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCommandResponseDTOList save(@Valid @RequestBody List<ProductCommandRequestDTO> dtoList) {
        return ProductCommandResponseDTOList.of(productService.save(ProductCommandVOList.of(dtoList)));
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam(value = "id", required = true) List<Long> itemIdList) {
        productService.delete(itemIdList);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ProductCommandResponseDTOList update(@Valid @RequestBody List<ProductCommandRequestDTO> dtoList) {
        return ProductCommandResponseDTOList.of(productService.update(ProductCommandVOList.of(dtoList)));
    }
}

