package com.healthapp.nutritionservice.service.Impl;

import com.healthapp.nutritionservice.dto.RecommendationRequestDTO;
import com.healthapp.nutritionservice.dto.FoodWithNutritionDTO;
import com.healthapp.nutritionservice.entity.Food;
import com.healthapp.nutritionservice.entity.Nutrition;
import com.healthapp.nutritionservice.enums.NutritionalCriterion;
import com.healthapp.nutritionservice.exception.InvalidNutritionalCriterionException;
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
    public List<FoodWithNutritionDTO> getRecommendedFoods(String criterion) {

        NutritionalCriterion nutritionalCriterion;
        try {
            nutritionalCriterion = NutritionalCriterion.valueOf(criterion.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle the case where the criterion is not recognized
            throw new InvalidNutritionalCriterionException(criterion);
        }

        List<Nutrition> nutritionList = nutritionRepository.findAll();

        Comparator<Nutrition> comparator = null;

        switch (nutritionalCriterion) {
            case VITAMIN:
                comparator = Comparator.comparing(Nutrition::getVitamins).reversed();
                break;
            case PROTEIN:
                comparator = Comparator.comparing(Nutrition::getProtein).reversed();
                break;
            case SUGAR:
                comparator = Comparator.comparing(Nutrition::getSugar).reversed();
                break;
            case CALORIE:
                comparator = Comparator.comparing(Nutrition::getCalorie).reversed();
                break;
            case SODIUM:
                comparator = Comparator.comparing(Nutrition::getSodium).reversed();
                break;
            case FIBER:
                comparator = Comparator.comparing(Nutrition::getFiber).reversed();
                break;
            case FAT:
                comparator = Comparator.comparing(Nutrition::getFat).reversed();
                break;
            case MINERALS:
                comparator = Comparator.comparing(Nutrition::getMinerals).reversed();
                break;
            case CARBOHYDRATES:
                comparator = Comparator.comparing(Nutrition::getCarbohydrates).reversed();
                break;
        }

        if (comparator != null) {
            Collections.sort(nutritionList, comparator);
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
