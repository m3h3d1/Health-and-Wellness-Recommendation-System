package com.healthapp.nutritionservice.service.Impl;

import com.healthapp.nutritionservice.entity.Nutrition;
import com.healthapp.nutritionservice.exception.NutritionNotFoundException;
import com.healthapp.nutritionservice.exception.NutritionServiceException;
import com.healthapp.nutritionservice.repository.NutritionRepository;
import com.healthapp.nutritionservice.service.NutritionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NutritionServiceImpl implements NutritionService {

    @Autowired
    private NutritionRepository nutritionRepository;

    @Override
    public Nutrition addNutrition(Nutrition nutrition) {
        return nutritionRepository.save(nutrition);
    }

    @Override
    public List<Nutrition> getAllNutrition() {
        return nutritionRepository.findAll();
    }

    @Override
    public Nutrition getNutritionById(UUID nutritionId) {
        return nutritionRepository.findById(nutritionId).orElse(null);
    }

    @Transactional
    @Override
    public Nutrition updateNutrition(UUID nutritionId, Nutrition updatedNutrition) {
        Nutrition existingNutrition = nutritionRepository.findById(nutritionId)
                .orElseThrow(() -> new NutritionNotFoundException("Nutrition data not found with ID: " + nutritionId));

//        existingNutrition.setRecipeId(updatedNutrition.getRecipeId());
//        existingNutrition.setFoodId(updatedNutrition.getFoodId());
        existingNutrition.setCalorie(updatedNutrition.getCalorie());
        existingNutrition.setProtein(updatedNutrition.getProtein());
        existingNutrition.setCarbohydrates(updatedNutrition.getCarbohydrates());
        existingNutrition.setFat(updatedNutrition.getFat());
        existingNutrition.setFiber(updatedNutrition.getFiber());
        existingNutrition.setVitamins(updatedNutrition.getVitamins());
        existingNutrition.setMinerals(updatedNutrition.getMinerals());
        existingNutrition.setSugar(updatedNutrition.getSugar());
        existingNutrition.setSodium(updatedNutrition.getSodium());
        existingNutrition.setCholesterol(updatedNutrition.getCholesterol());
        existingNutrition.setServingSize(updatedNutrition.getServingSize());
        existingNutrition.setAllergenic(updatedNutrition.isAllergenic());
        existingNutrition.setVegetarian(updatedNutrition.isVegetarian());

        return nutritionRepository.save(existingNutrition);
    }

    public void deleteNutrition(UUID nutritionId) {
        try {
            Nutrition existingNutrition = nutritionRepository.findById(nutritionId)
                    .orElseThrow(() -> new NutritionNotFoundException("Nutrition data not found with ID: " + nutritionId));

            nutritionRepository.delete(existingNutrition);
        } catch (Exception e) {
            throw new NutritionServiceException("Failed to delete nutrition data", e);
        }
    }
}