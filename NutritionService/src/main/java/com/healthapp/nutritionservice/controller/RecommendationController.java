package com.healthapp.nutritionservice.controller;

import com.healthapp.nutritionservice.dto.RecommendationRequestDTO;
import com.healthapp.nutritionservice.dto.FoodWithNutritionDTO;
import com.healthapp.nutritionservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/nutrition/recommendation")
public class RecommendationController {
    @Autowired
    RecommendationService recommendationService;

    @GetMapping("/{criterion}")
    public ResponseEntity<?> getRecommendedFoods(@PathVariable String criterion) {
        List<FoodWithNutritionDTO> foodWithNutritionDTOs = recommendationService.getRecommendedFoods(criterion);
        return new ResponseEntity<>(foodWithNutritionDTOs, HttpStatus.OK);
    }
}
