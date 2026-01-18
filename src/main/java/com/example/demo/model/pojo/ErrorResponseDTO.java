package com.example.demo.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {
    private String errorCode;
    private String errorMessage;
}