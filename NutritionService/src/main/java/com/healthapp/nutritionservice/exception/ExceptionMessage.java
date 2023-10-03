package com.healthapp.nutritionservice.exception;

public class ExceptionMessage extends RuntimeException {
    public ExceptionMessage(String message) {
        super(message);
    }
    public ExceptionMessage(String message, Exception ex) {
        super(message + ex.getMessage());
    }
}