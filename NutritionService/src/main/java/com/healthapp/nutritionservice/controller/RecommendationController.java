package com.healthapp.nutritionservice.controller;

import com.healthapp.nutritionservice.dto.RecommendationRequestDTO;
import com.healthapp.nutritionservice.dto.FoodWithNutritionDTO;
import com.healthapp.nutritionservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nutrition")
public class RecommendationController {
    @Autowired
    RecommendationService recommendationService;

    @GetMapping("/recommendation")
    public ResponseEntity<?> getAllRecipies(@RequestBody RecommendationRequestDTO requestDTO) {
        List<FoodWithNutritionDTO> foodWithNutritionDTOs = recommendationService.getRecommendedFoods(requestDTO);
        return new ResponseEntity<>(foodWithNutritionDTOs, HttpStatus.OK);
    }
}
