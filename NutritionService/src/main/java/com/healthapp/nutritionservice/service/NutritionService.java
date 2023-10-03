package com.healthapp.nutritionservice.service;

import com.healthapp.nutritionservice.entity.Nutrition;

import java.util.List;
import java.util.UUID;

public interface NutritionService {
    public Nutrition addNutrition(Nutrition nutrition);
    public Nutrition getNutritionById(UUID nutritionId);
    public List<Nutrition> getAllNutrition();
    public Nutrition updateNutrition(UUID nutritionId, Nutrition updatedNutrition);
    public void deleteNutrition(UUID nutritionId);
}