package com.healthapp.nutritionservice.controller;

import com.healthapp.nutritionservice.entity.Food;
import com.healthapp.nutritionservice.entity.Nutrition;
import com.healthapp.nutritionservice.service.FoodService;
import com.healthapp.nutritionservice.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @PostMapping("/add")
    public ResponseEntity<String> addNutrition(@RequestBody Food food) {
        foodService.addFood(food);
        return new ResponseEntity<>("Food Data Added Successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Food>> getAllFoods() {
        try {
            List<Food> foodList = foodService.getAllFoods();
            return new ResponseEntity<>(foodList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{foodId}")
    public ResponseEntity<Food> getNutritionById(@PathVariable UUID foodId) {
        Food food = foodService.getFoodById(foodId);

        if (food != null) {
            return new ResponseEntity<>(food, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{foodId}")
    public ResponseEntity<String> updateNutrition(
            @PathVariable UUID foodId,
            @RequestBody Food updatedFood) {
        foodService.updateFood(foodId, updatedFood);
        return new ResponseEntity<>("Food Data Updated Successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<String> deleteNutrition(@PathVariable UUID foodId) {
        try {
            foodService.deleteFood(foodId);
            return new ResponseEntity<>("Food Data Deleted Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete food data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}