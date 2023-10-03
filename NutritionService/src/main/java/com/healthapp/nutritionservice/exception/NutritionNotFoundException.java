package com.healthapp.nutritionservice.exception;

public class NutritionNotFoundException extends RuntimeException {
    public NutritionNotFoundException(String message) {
        super(message);
    }
}