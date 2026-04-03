package com.smartschool.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // 🌟 This watches all Controllers for errors
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllErrors(Exception ex) {
        Map<String, String> response = new HashMap<>();

        // 🛡️ Logic: If the error contains "duplicate key", it's usually a duplicate email
        if (ex.getMessage().contains("duplicate key") || ex.getMessage().contains("Unique")) {
            response.put("error", "This email is already registered. Please use another one.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}