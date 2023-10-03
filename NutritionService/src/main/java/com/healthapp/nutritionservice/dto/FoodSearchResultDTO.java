package com.healthapp.nutritionservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class FoodSearchResultDTO {
    private UUID foodId;
    private String name;
    private String category;
    private String description;
    private boolean organic;
    private List<RecipeDTO> recipes;
}
