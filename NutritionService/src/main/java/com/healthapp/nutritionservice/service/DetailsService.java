package com.healthapp.nutritionservice.service;

import com.healthapp.nutritionservice.dto.FoodDetailsDTO;

public interface DetailsService {
    public FoodDetailsDTO getFoodDetailsByName(String word);
}
