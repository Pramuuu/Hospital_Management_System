package com.hospital.management.exception;

import com.hospital.management.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> globalExceptionHandler(Exception ex, WebRequest request) {
        ApiResponse<Void> response = ApiResponse.error("An error occurred: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}