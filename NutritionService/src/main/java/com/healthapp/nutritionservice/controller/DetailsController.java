package com.healthapp.nutritionservice.controller;

import com.healthapp.nutritionservice.dto.FoodDetailsDTO;
import com.healthapp.nutritionservice.entity.Food;
import com.healthapp.nutritionservice.entity.Recipe;
import com.healthapp.nutritionservice.service.DetailsService;
import com.healthapp.nutritionservice.service.FoodService;
import com.healthapp.nutritionservice.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/nutrition/details")
public class DetailsController {

    @Autowired
    private DetailsService detailsService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private FoodService foodService;

    @GetMapping("/{word}") // Get all details of a food (same as get by name).
    public ResponseEntity<FoodDetailsDTO> getFoodDetailsByName(@PathVariable String word) {
        FoodDetailsDTO foodDetailsDTO = detailsService.getFoodDetailsByName(word);

        if (foodDetailsDTO != null) {
            return new ResponseEntity<>(foodDetailsDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable UUID recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);

        if (recipe != null) {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/food/{foodId}")
    public ResponseEntity<Food> getNutritionById(@PathVariable UUID foodId) {
        Food food = foodService.getFoodById(foodId);

        if (food != null) {
            return new ResponseEntity<>(food, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}