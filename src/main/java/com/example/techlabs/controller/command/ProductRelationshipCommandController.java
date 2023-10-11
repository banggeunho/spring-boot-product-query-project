package com.example.techlabs.controller.command;

import com.example.techlabs.controller.command.dto.ProductRelationCommandRequestDTO;
import com.example.techlabs.controller.command.dto.ProductRelationCommandResponseDTO;
import com.example.techlabs.service.ProductRelationService;
import com.example.techlabs.service.vo.command.ProductRelationCommandVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product-relationships")
public class ProductRelationshipCommandController {

    private final ProductRelationService productRelationService;
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRelationCommandResponseDTO save(@Valid @RequestBody ProductRelationCommandRequestDTO dto) {
        return ProductRelationCommandResponseDTO.of(productRelationService.save(ProductRelationCommandVO.of(dto)));
    }

    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam(value = "target_id") Long targetItemId,
                       @RequestParam(value = "result_id") Long resultItemId) {
        productRelationService.delete(targetItemId, resultItemId);
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ProductRelationCommandResponseDTO update(@Valid @RequestBody ProductRelationCommandRequestDTO dto) {
        return ProductRelationCommandResponseDTO.of(productRelationService.update(ProductRelationCommandVO.of(dto)));

    }
}

