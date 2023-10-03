package com.healthapp.nutritionservice.controller;

import com.healthapp.nutritionservice.entity.Food;
import com.healthapp.nutritionservice.entity.Recipe;
import com.healthapp.nutritionservice.service.FoodService;
import com.healthapp.nutritionservice.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @PostMapping("/add")
    public ResponseEntity<String> addRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return new ResponseEntity<>("Recipe Data Added Successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Recipe>> getAllRecipies() {
        try {
            List<Recipe> recipeList = recipeService.getAllRecipies();
            return new ResponseEntity<>(recipeList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable UUID recipeId) {
        Recipe recipe = recipeService.getRecipeById(recipeId);

        if (recipe != null) {
            return new ResponseEntity<>(recipe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{recipeId}")
    public ResponseEntity<String> updateRecipe(
            @PathVariable UUID recipeId,
            @RequestBody Recipe updatedRecipe) {
        recipeService.updateRecipe(recipeId, updatedRecipe);
        return new ResponseEntity<>("Recipe Data Updated Successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{recipeId}")
    public ResponseEntity<String> deleteRecipe(@PathVariable UUID recipeId) {
        try {
            recipeService.deleteRecipe(recipeId);
            return new ResponseEntity<>("Recipe Data Deleted Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete recipe data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
