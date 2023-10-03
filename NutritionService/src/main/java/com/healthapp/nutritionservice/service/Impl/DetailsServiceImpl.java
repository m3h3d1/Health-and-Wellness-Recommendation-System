package com.healthapp.nutritionservice.service.Impl;

import com.healthapp.nutritionservice.dto.FoodDetailsDTO;
import com.healthapp.nutritionservice.entity.Food;
import com.healthapp.nutritionservice.repository.FoodRepository;
import com.healthapp.nutritionservice.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailsServiceImpl implements DetailsService {
    @Autowired
    private FoodRepository foodRepository;

    public FoodDetailsDTO getFoodDetailsByName(String word) {
        Food matchingFood = foodRepository.findByNameContainingIgnoreCase(word);

        if (matchingFood != null) {
            FoodDetailsDTO foodDetailsDTO = new FoodDetailsDTO();
            foodDetailsDTO.setFoodId(matchingFood.getFoodId());
            foodDetailsDTO.setName(matchingFood.getName());
            foodDetailsDTO.setCategory(matchingFood.getCategory());
            foodDetailsDTO.setDescription(matchingFood.getDescription());
            return foodDetailsDTO;
        } else {
            return null; // Food not found
        }
    }
}
