package com.healthapp.recommendationserviceauto.service.impl;

import com.healthapp.recommendationserviceauto.domain.*;
import com.healthapp.recommendationserviceauto.model.DietRecommendationDto;
import com.healthapp.recommendationserviceauto.model.SleepRecommendationDto;
import com.healthapp.recommendationserviceauto.model.SuggestedFoodListDto;
import com.healthapp.recommendationserviceauto.networkmanager.FoodFeignClient;
import com.healthapp.recommendationserviceauto.repository.DietRecommendationRepository;
import com.healthapp.recommendationserviceauto.repository.HealthRepository;
import com.healthapp.recommendationserviceauto.repository.MealRepository;
import com.healthapp.recommendationserviceauto.service.DietService;
import com.healthapp.recommendationserviceauto.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DietServiceImpl implements DietService {
    @Autowired
    private HealthRepository healthRepository;
    @Autowired
    private FoodFeignClient foodFeignClient;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private DietRecommendationRepository dietRecommendationRepository;
    private String message;
    @Override
    public DietRecommendationDto recommend(UUID userId) {
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);
        DietRecommendation dietRecommendation = new DietRecommendation();
        DietRecommendationDto dietRecommendationDto = new DietRecommendationDto();
        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            List<Height> heights = health.getHeights();
            List<Weight> weights = health.getWeights();
//            List<Diabetes> diabetes = health.getDiabetes();
            Double latestWeight = weights.get(weights.size() - 1).getWeightInKg();
            Double latestHeight = heights.get(heights.size()-1).getHeightInCm();
//            Double latestSugarLevel = diabetes.get(weights.size() - 1).getSugarLevel();
            Integer age = health.getAge();
            Integer goalWeight = health.getGoalWeight();
            Integer targetPeriod = health.getTargetPeriod();
            Double activityFactor = health.getDailyActivity().getFactor();
            String gender = health.getGender();

            // Calculate Daily Calorie requirements
            double heightInMeters = latestHeight / 100.0;
            // Calculate BMI
            double bmi = latestWeight / (heightInMeters * heightInMeters);
            message = generateMessage(bmi);
            // Calculate BMR
            double bmr;
            if (gender.equals("Male")) {
                bmr = 88.362 + (13.397 * latestWeight) + (4.799 * latestHeight) - (5.677 * age);
            } else {
                bmr = 447.593 + (9.247 * latestWeight) + (3.098 * latestHeight) - (4.330 * age);
            }

            double dailyCalorieRequirements = bmr * activityFactor;
            Double weightDifference = goalWeight-latestWeight;
            Double dailyWeightTarget = weightDifference/(targetPeriod * 30);
            Double dailyCalorieTarget = dailyWeightTarget * 7200; // For 1 kg
            Double recommendedDailyCalorieRequirements = dailyCalorieRequirements - dailyCalorieTarget;

            double breakfastPercentage = 0.20; // 20 percent
            double lunchPercentage = 0.40; // 50 percent
            double dinnerPercentage = 0.30; // 30 percent
            double snacksPercentage = 0.10; // 10 percent

            // Calculate calorie portions for each meal
            double breakfastCalories = recommendedDailyCalorieRequirements * breakfastPercentage;
            double lunchCalories = recommendedDailyCalorieRequirements * lunchPercentage;
            double dinnerCalories = recommendedDailyCalorieRequirements * dinnerPercentage;
            double snacksCalories = recommendedDailyCalorieRequirements * snacksPercentage;
            List<Meal> breakfastMealList = preferFood(breakfastCalories, "fiber");
            List<Meal> lunchMealList = preferFood(lunchCalories, "protein");
            List<Meal> dinnerMealList = preferFood(dinnerCalories, "carbohydrates");
            List<Meal> snacksMealList = preferFood(snacksCalories, "vitamin");
            //Save in DietRecommendation
            dietRecommendation.setUserId(userId);
            dietRecommendation.setHeight(latestHeight);
            dietRecommendation.setWeight(latestWeight);
            dietRecommendation.setGoalWeight(goalWeight);
            dietRecommendation.setTargetPeriod(targetPeriod);
            dietRecommendation.setRecommendationTime(LocalDateTime.now());
            dietRecommendation.setRecommendationMessage(message);
            dietRecommendation.setBreakfast(breakfastMealList);
            dietRecommendation.setLunch(lunchMealList);
            dietRecommendation.setDinner(dinnerMealList);
            dietRecommendation.setSnacks(snacksMealList);
            dietRecommendationRepository.save(dietRecommendation);

            //Send response as DTO
            dietRecommendationDto.setDate(LocalDateTime.now());
            dietRecommendationDto.setMessage(message);
            dietRecommendationDto.setBreakfast(breakfastMealList);
            dietRecommendationDto.setLunch(lunchMealList);
            dietRecommendationDto.setSnacks(snacksMealList);
            dietRecommendationDto.setDinner(dinnerMealList);

            return dietRecommendationDto;


        }
        return null;
    }

    @Override
    public DietRecommendation getRecommendationById(UUID recommendationId) {
        return dietRecommendationRepository.findById(recommendationId).get();
    }


    private List<Meal> preferFood(Double targetCalorie, String category){

        ResponseEntity<List<SuggestedFoodListDto>> foodList = foodFeignClient.getFoodByCategory(category);
        List<SuggestedFoodListDto> suggestedFoodList=foodList.getBody();
        List<Meal> mealList = new ArrayList<>();
        Double caloriesCalculatedFromFood =0.0;
        for (int i=0;i<suggestedFoodList.size();i++) {
                SuggestedFoodListDto foodListResponse = suggestedFoodList.get(i);
                caloriesCalculatedFromFood += foodListResponse.getCalorie();
                if(caloriesCalculatedFromFood>=targetCalorie){
                    break;
                }
                if(i==suggestedFoodList.size()-1){
                    i=0;
                }
                Meal meal = new Meal();
                meal.setDescription(foodListResponse.getDescription());
                meal.setCalories(foodListResponse.getCalorie());
                mealRepository.save(meal);
                mealList.add(meal);
            }
        return mealList;
    }
    private String generateMessage(Double bmi){
        String message;
        if (bmi < 18.5) {
            message="You may be underweight. Consider following the recommended diet";
        } else if (bmi >= 18.5 && bmi < 24.9) {
            message="Congratulations! Your weight falls within the healthy range. You can follow the recommended diet";
        } else if (bmi >= 25.0 && bmi < 29.9) {
            message="You may be overweight. Consider adopting a healthier lifestyle by following the recommended diet.";
        } else {
            message="You are in the obese category. It is highly recommended to follow the provided diet routines.";
        }
        return message;
    }

}
