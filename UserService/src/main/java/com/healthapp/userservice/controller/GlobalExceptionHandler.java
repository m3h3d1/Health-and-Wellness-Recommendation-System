package com.healthapp.userservice.controller;

import com.healthapp.userservice.exception.*;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ConstraintViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ConstraintViolationException",
                "Please provide the valid and required data",
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "DataIntegrityViolationException",
                "Duplicate entry is not allowed",
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(ContactUpdateException.class)
    public ResponseEntity<ErrorResponse> handleContactUpdateException(ContactUpdateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ContactUpdateException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfileUpdateException.class)
    public ResponseEntity<ErrorResponse> handleProfileUpdateException(ProfileUpdateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "ProfileUpdateException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<ErrorResponse> handleUserUpdateException(UserUpdateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "UserUpdateException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ErrorResponse> handlePasswordException(PasswordException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "PasswordException",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                new Date()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
