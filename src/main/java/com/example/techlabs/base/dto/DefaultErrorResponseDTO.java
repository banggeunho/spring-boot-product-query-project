package com.example.techlabs.base.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultErrorResponseDTO {
    private String errorMessage;
    private String errorStackTrace;
}
