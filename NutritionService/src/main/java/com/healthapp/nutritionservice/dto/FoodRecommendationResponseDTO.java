package com.healthapp.nutritionservice.dto;

import com.healthapp.nutritionservice.entity.Food;
import lombok.Setter;

import java.util.List;

@Setter
public class FoodRecommendationResponseDTO {
    private String nutritionalCriterion;
    private List<Food> recommendedFoods;
}
