package com.healthapp.nutritionservice.exception;

public class InvalidNutritionalCriterionException extends RuntimeException {
    public InvalidNutritionalCriterionException(String criterion) {
        super("Invalid nutritional criterion: " + criterion);
    }
}