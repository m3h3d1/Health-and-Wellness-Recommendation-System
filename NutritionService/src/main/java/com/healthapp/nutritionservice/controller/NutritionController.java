package com.healthapp.nutritionservice.controller;

import com.healthapp.nutritionservice.entity.Nutrition;
import com.healthapp.nutritionservice.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/nutrition")
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    @PostMapping("/add")
    public ResponseEntity<String> addNutrition(@RequestBody Nutrition nutrition) {
        nutritionService.addNutrition(nutrition);
        return new ResponseEntity<>("Nutrition Data Added Successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Nutrition>> getAllNutrition() {
        try {
            List<Nutrition> nutritionList = nutritionService.getAllNutrition();
            return new ResponseEntity<>(nutritionList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{nutritionId}")
    public ResponseEntity<Nutrition> getNutritionById(@PathVariable UUID nutritionId) {
        Nutrition nutrition = nutritionService.getNutritionById(nutritionId);

        if (nutrition != null) {
            return new ResponseEntity<>(nutrition, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{nutritionId}")
    public ResponseEntity<String> updateNutrition(
            @PathVariable UUID nutritionId,
            @RequestBody Nutrition updatedNutrition) {
        nutritionService.updateNutrition(nutritionId, updatedNutrition);
        return new ResponseEntity<>("Nutrition Data Updated Successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{nutritionId}")
    public ResponseEntity<String> deleteNutrition(@PathVariable UUID nutritionId) {
        try {
            nutritionService.deleteNutrition(nutritionId);
            return new ResponseEntity<>("Nutrition Data Deleted Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete nutrition data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}