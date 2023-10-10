package com.example.techlabs.controller.command;

import com.example.techlabs.controller.command.dto.ProductRelationCommandRequestDTO;
import com.example.techlabs.controller.command.dto.ProductRelationCommandResponseDTO;
import com.example.techlabs.service.ProductRelationService;
import com.example.techlabs.service.vo.command.ProductRelationCommandVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-relationships")
public class ProductRelationshipCommandController {

    private final ProductRelationService productRelationService;
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRelationCommandResponseDTO save(@RequestBody ProductRelationCommandRequestDTO dto) {
        return ProductRelationCommandResponseDTO.of(productRelationService.save(ProductRelationCommandVO.of(dto)));
    }

//    @DeleteMapping("")
//    @ResponseStatus(HttpStatus.OK)
//    public void delete(@RequestParam(value = "id", required = true) List<Long> itemIdList) {
//        productService.delete(itemIdList);
//    }
//
//    @PutMapping("")
//    @ResponseStatus(HttpStatus.OK)
//    public void update(@RequestBody List<ProductCommandRequestDTO> dtoList) {
//        productService.update(
//                ProductCommandVOList.builder()
//                        .productCommandVOList(
//                                dtoList.stream()
//                                        .map(ProductCommandVO::of)
//                                        .collect(Collectors.toList()))
//                        .build()
//        );
//
//    }
}

