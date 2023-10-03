package com.healthapp.nutritionservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecipeDTO {
    private UUID recipeId;
    private UUID foodId;
    private String cookingProcess;
    private int cookingTimeMinutes;
}

