package com.example.techlabs.base.common;

import com.example.techlabs.base.dto.DefaultErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.PersistenceException;
import java.util.Arrays;

@RestControllerAdvice(basePackages = "com.example.techlabs.controller")
public class DefaultExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(PersistenceException.class)
    public DefaultErrorResponseDTO exceptionHandler(PersistenceException e) {
        return DefaultErrorResponseDTO.builder()
                .errorMessage(e.getMessage())
                .errorStackTrace(Arrays.toString(e.getStackTrace()))
                .build();
    }
}
