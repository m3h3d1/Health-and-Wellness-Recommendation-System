package com.healthapp.nutritionservice.service;

import com.healthapp.nutritionservice.dto.FoodRecommendationRequestDTO;
import com.healthapp.nutritionservice.dto.FoodRecommendationResponseDTO;
import com.healthapp.nutritionservice.entity.Food;

import java.util.List;

public interface FoodRecommendationService {
    public FoodRecommendationResponseDTO getRecommendedFoods(FoodRecommendationRequestDTO requestDTO);
}
