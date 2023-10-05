package com.healthapp.nutritionservice.service.Impl;

import com.healthapp.nutritionservice.entity.Food;
import com.healthapp.nutritionservice.entity.Recipe;
import com.healthapp.nutritionservice.exception.*;
import com.healthapp.nutritionservice.repository.FoodRepository;
import com.healthapp.nutritionservice.repository.RecipeRepository;
import com.healthapp.nutritionservice.service.RecipeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
//    @Override
//    @Transactional // Ensure that this method runs within a transaction
//    public Recipe addRecipe(Recipe recipe, Set<UUID> foodIds) {
//        Set<Food> associatedFoods = new HashSet<>();
//
//        for (UUID foodId : foodIds) {
//            Food food = foodRepository.findById(foodId)
//                    .orElseThrow(() -> new FoodNotFoundException("Food not found with ID: " + foodId));
//
//            associatedFoods.add(food);
//        }
//
//        recipe.setFoods(associatedFoods);
//
//        return recipeRepository.save(recipe);
//    }

    @Override
    public List<Recipe> getAllRecipies() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(UUID recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }

    @Override
    @Transactional
    public Recipe updateRecipe(UUID recipeId, Recipe updatedRecipe) {
        Recipe existingRecipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new FoodNotFoundException("Recipe data not found with ID: " + recipeId));

        existingRecipe.setCookingProcess(updatedRecipe.getCookingProcess());
        existingRecipe.setCookingTimeMinutes(updatedRecipe.getCookingTimeMinutes());
        return recipeRepository.save(existingRecipe);
    }

    @Override
    public void deleteRecipe(UUID recipeId) {
        try {
            Recipe existingRecipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new RecipeNotFoundException("Recipe data not found with ID: " + recipeId));

            recipeRepository.delete(existingRecipe);
        } catch (Exception e) {
            throw new ExceptionMessage("Failed to delete recipe data", e);
        }
    }
}
