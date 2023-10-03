package com.healthapp.nutritionservice.service;

import com.healthapp.nutritionservice.entity.Food;
import com.healthapp.nutritionservice.entity.Recipe;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RecipeService {
    public Recipe addRecipe(Recipe recipe);
//    public Recipe addRecipe(Recipe recipe, Set<UUID> foodIds);
    public List<Recipe> getAllRecipies();
    public Recipe getRecipeById(UUID recipeId);
    public Recipe updateRecipe(UUID recipeId, Recipe updatedRecipe);
    public void deleteRecipe(UUID recipeId);
}
