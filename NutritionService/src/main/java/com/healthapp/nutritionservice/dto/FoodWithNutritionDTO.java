package com.healthapp.nutritionservice.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FoodWithNutritionDTO {
    private UUID foodId;
    private String name;
    private String category;
    private String description;
    private double calorie;
    private double protein;
    private double carbohydrates;
    private double fat;
    private double fiber;
    private double vitamins;
    private double minerals;
    private double sugar;
    private double sodium;
    private double cholesterol;
}
