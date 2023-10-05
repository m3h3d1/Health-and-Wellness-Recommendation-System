package com.healthapp.nutritionservice.service.Impl;

import com.healthapp.nutritionservice.dto.RecommendationRequestDTO;
import com.healthapp.nutritionservice.dto.FoodWithNutritionDTO;
import com.healthapp.nutritionservice.entity.Food;
import com.healthapp.nutritionservice.entity.Nutrition;
import com.healthapp.nutritionservice.repository.FoodRepository;
import com.healthapp.nutritionservice.repository.NutritionRepository;
import com.healthapp.nutritionservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private NutritionRepository nutritionRepository;

    @Override
    public List<FoodWithNutritionDTO> getRecommendedFoods(RecommendationRequestDTO requestDTO) {

        String criterion = requestDTO.getNutritionalCriterion();
        List<Nutrition> nutritionList = nutritionRepository.findAll();

        if(criterion == "vitamin") {
            Collections.sort(nutritionList, Comparator.comparing(Nutrition::getVitamins).reversed());
        }
        else if(criterion == "protein") {
            Collections.sort(nutritionList, Comparator.comparing(Nutrition::getProtein).reversed());
        }
        else if(criterion == "sugar") {
            Collections.sort(nutritionList, Comparator.comparing(Nutrition::getSugar).reversed());
        }
        else if(criterion == "calorie") {
            Collections.sort(nutritionList, Comparator.comparing(Nutrition::getCalorie).reversed());
        }
        else if(criterion == "sodium") {
            Collections.sort(nutritionList, Comparator.comparing(Nutrition::getSodium).reversed());
        }
        else if(criterion == "fiber") {
            Collections.sort(nutritionList, Comparator.comparing(Nutrition::getFiber).reversed());
        }
        else if(criterion == "fat") {
            Collections.sort(nutritionList, Comparator.comparing(Nutrition::getFat).reversed());
        }
        else if(criterion == "minerals") {
            Collections.sort(nutritionList, Comparator.comparing(Nutrition::getMinerals).reversed());
        }
        else if(criterion == "carborhydrates") {
            Collections.sort(nutritionList, Comparator.comparing(Nutrition::getCarbohydrates).reversed());
        }

        List<FoodWithNutritionDTO> recommendFoods = new ArrayList<>();
        int cnt=0;
        for(Nutrition nutrition: nutritionList) {
            FoodWithNutritionDTO foodWithNutritionDTO = new FoodWithNutritionDTO();
            Food food = foodRepository.findById(nutrition.getFood().getFoodId()).get();

            foodWithNutritionDTO.setFoodId(food.getFoodId());
            foodWithNutritionDTO.setName(food.getName());
            foodWithNutritionDTO.setCategory(food.getCategory());
            foodWithNutritionDTO.setDescription(food.getDescription());
            foodWithNutritionDTO.setCalorie(nutrition.getCalorie());
            foodWithNutritionDTO.setCarbohydrates(nutrition.getCarbohydrates());
            foodWithNutritionDTO.setFat(nutrition.getFat());
            foodWithNutritionDTO.setFiber(nutrition.getFiber());
            foodWithNutritionDTO.setProtein(nutrition.getProtein());
            foodWithNutritionDTO.setSodium(nutrition.getSodium());
            foodWithNutritionDTO.setMinerals(nutrition.getMinerals());
            foodWithNutritionDTO.setVitamins(nutrition.getVitamins());
            foodWithNutritionDTO.setSugar(nutrition.getSugar());
            foodWithNutritionDTO.setCholesterol(nutrition.getCholesterol());

            recommendFoods.add(foodWithNutritionDTO);

            if(++cnt==3) break;
        }
        return recommendFoods;
    }
}
