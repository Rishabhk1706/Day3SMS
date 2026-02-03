package com.example.day3sms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
    private Map<String, String> errors;
}
