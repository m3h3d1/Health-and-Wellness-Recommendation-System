package com.healthapp.nutritionservice.service.Impl;

import com.healthapp.nutritionservice.entity.Food;
import com.healthapp.nutritionservice.entity.Nutrition;
import com.healthapp.nutritionservice.exception.FoodNotFoundException;
import com.healthapp.nutritionservice.exception.NutritionNotFoundException;
import com.healthapp.nutritionservice.exception.NutritionServiceException;
import com.healthapp.nutritionservice.repository.FoodRepository;
import com.healthapp.nutritionservice.service.FoodService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food getFoodById(UUID foodId) {
        return foodRepository.findById(foodId).orElse(null);
    }

    @Override
    @Transactional
    public Food updateFood(UUID foodId, Food updatedFood) {
        Food existingFood = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodNotFoundException("Food data not found with ID: " + foodId));

        existingFood.setName(updatedFood.getName());
        existingFood.setCategory(updatedFood.getCategory());
        existingFood.setDescription(updatedFood.getDescription());
        return foodRepository.save(existingFood);
    }

    @Override
    public void deleteFood(UUID foodId) {
        try {
            Food existingFood = foodRepository.findById(foodId)
                    .orElseThrow(() -> new NutritionNotFoundException("Food data not found with ID: " + foodId));

            foodRepository.delete(existingFood);
        } catch (Exception e) {
            throw new NutritionServiceException("Failed to delete food data", e);
        }
    }
}