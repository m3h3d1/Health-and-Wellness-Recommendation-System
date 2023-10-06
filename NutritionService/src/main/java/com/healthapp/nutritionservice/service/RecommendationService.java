package com.healthapp.nutritionservice.service;

import com.healthapp.nutritionservice.dto.RecommendationRequestDTO;
import com.healthapp.nutritionservice.dto.FoodWithNutritionDTO;

import java.util.List;

public interface RecommendationService {
//    public List<FoodWithNutritionDTO> getRecommendedFoods(RecommendationRequestDTO requestDTO);
    public List<FoodWithNutritionDTO> getRecommendedFoods(String criterion);
}
