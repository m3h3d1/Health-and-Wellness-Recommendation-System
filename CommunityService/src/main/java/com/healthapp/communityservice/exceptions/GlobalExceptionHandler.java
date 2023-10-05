package com.healthapp.communityservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customCommonException(CustomException exception, HttpServletRequest request) {
        return generateResponse(exception, request);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointer(NullPointerException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new com.healthapp.communityservice.models.errors.ErrorResponse(
                "NullPointerException", "Failed to process null value",
                "Built in", "Null value could not be referenced", HttpStatus.BAD_REQUEST.toString(),
                new Date(), request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<?> generateResponse(CustomException exception, HttpServletRequest request){
        com.healthapp.communityservice.models.errors.ErrorResponse response = new com.healthapp.communityservice.models.errors.ErrorResponse(
                exception.getExceptionName(), exception.getOperation(), exception.getType(),
                        exception.getMessage(), exception.getHttpStatus().toString(), new Date(), request.getRequestURI()
        );
        return new ResponseEntity<>(response, exception.getHttpStatus());
    }
}
