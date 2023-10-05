package com.healthapp.nutritionservice.exception;

public class NutritionServiceException extends RuntimeException {
    public NutritionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}