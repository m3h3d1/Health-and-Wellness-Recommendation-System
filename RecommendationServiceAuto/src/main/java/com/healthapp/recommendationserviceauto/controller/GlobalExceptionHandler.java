package com.healthapp.recommendationserviceauto.controller;

import com.healthapp.recommendationserviceauto.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "UnhandledException",
                "Sorry the requested operation is not possible",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({EmptyResultException.class})
    public ResponseEntity<ErrorResponse> handleEmptyDataException(EmptyResultException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "EmptyResultException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({BloodPressureException.class})
    public ResponseEntity<ErrorResponse> handleBloodPressureException(BloodPressureException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "BloodPressureException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({SugarLevelException.class})
    public ResponseEntity<ErrorResponse> handleSugarLevelException(SugarLevelException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "SugarLevelException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({WeightException.class})
    public ResponseEntity<ErrorResponse> handleWeightException(WeightException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "WeightException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({HeightException.class})
    public ResponseEntity<ErrorResponse> handleHeightException(HeightException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "HeightException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedUserException(UnauthorizedUserException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "UnauthorizedUserException",
                ex.getMessage(),
                HttpStatus.FORBIDDEN.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
