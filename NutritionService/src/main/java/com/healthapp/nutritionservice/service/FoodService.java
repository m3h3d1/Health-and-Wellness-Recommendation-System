package com.healthapp.nutritionservice.service;

import com.healthapp.nutritionservice.entity.Food;

import java.util.List;
import java.util.UUID;

public interface FoodService {
    public Food addFood(Food food);
    public List<Food> getAllFoods();
    public Food getFoodById(UUID foodId);
    public Food updateFood(UUID foodId, Food updatedFood);
    public void deleteFood(UUID foodId);
}
