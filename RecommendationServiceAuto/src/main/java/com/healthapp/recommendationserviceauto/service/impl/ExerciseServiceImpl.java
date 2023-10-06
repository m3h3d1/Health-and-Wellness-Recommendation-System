package com.healthapp.recommendationserviceauto.service.impl;

import com.healthapp.recommendationserviceauto.domain.*;
import com.healthapp.recommendationserviceauto.model.ExerciseRecommendationDto;
import com.healthapp.recommendationserviceauto.model.ExerciseRequestDto;
import com.healthapp.recommendationserviceauto.model.ProfileResponseDto;
import com.healthapp.recommendationserviceauto.networkmanager.UserFeignClient;
import com.healthapp.recommendationserviceauto.repository.BloodPressureRepository;
import com.healthapp.recommendationserviceauto.repository.ExerciseRecommendationRepository;
import com.healthapp.recommendationserviceauto.repository.ExerciseRepository;
import com.healthapp.recommendationserviceauto.repository.HealthRepository;
import com.healthapp.recommendationserviceauto.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Autowired
    private HealthRepository healthRepository;
    @Autowired
    private BloodPressureRepository bloodPressureRepository;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseRecommendationRepository exerciseRecommendationRepository;
    @Override
    public ExerciseRecommendationDto recommend(UUID userId) {
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);
        ExerciseRecommendation exerciseRecommendation = new ExerciseRecommendation();
        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            List<BloodPressure> bloodPressures = health.getBloodPressures();
            List<Weight> weights = health.getWeights();
            List<Diabetes> diabetes = health.getDiabetes();
            List<Disease> diseases = health.getDiseases();

            Double latestBloodPressure = bloodPressures.get(bloodPressures.size()-1).getPressure();
            Double latestWeight = weights.get(weights.size()-1).getWeightInKg();
            Double latestSugarLevel = diabetes.get(weights.size()-1).getSugarLevel();
            Integer age = health.getAge();
            Integer goalWeight = health.getGoalWeight();
            Integer targetPeriod = health.getTargetPeriod();

            String insights = "";

            // Check blood pressure
            if (latestBloodPressure > 140.0) {
                insights += "Your blood pressure is high. ";
            } else if (latestBloodPressure < 90.0) {
                insights += "Your blood pressure is low. ";
            }

            // Check weight and weight goal
            if (latestWeight > goalWeight) {
                insights += "You have exceeded your goal weight. ";
            } else if (latestWeight < goalWeight) {
                insights += "You are below your goal weight. ";
            }

            // Check sugar level (assuming sugar level is measured in mg/dL)
            if (latestSugarLevel > 180.0) {
                insights += "Your blood sugar level is high. ";
            } else if (latestSugarLevel < 70.0) {
                insights += "Your blood sugar level is low. ";
            }

            // Check age and target period
            if (age > 40 && targetPeriod < 6) {
                insights += "Given your age and short target period, it's important to focus on sustainable fitness goals. ";
            }

            // Check overall health
            if (insights.isEmpty()) {
                insights = "You are maintaining good health. Keep up the good work!";
            } else {
                insights += "Consider consulting a healthcare provider for personalized advice.";
            }
            String recommendationText = "";

            if (!insights.isEmpty()) {
                if (latestBloodPressure > 140.0) {
                    recommendationText += "Consider avoiding strenuous exercises and consult a healthcare provider for your high blood pressure. ";
                } else if (latestBloodPressure < 90.0) {
                    recommendationText += "Consider light-intensity exercises and consult a healthcare provider for your low blood pressure. ";
                }

                if (latestWeight > goalWeight) {
                    recommendationText += "Consider focusing on weight maintenance exercises to achieve your goal weight. ";
                } else if (latestWeight < goalWeight) {
                    recommendationText += "Consider adding strength and resistance training to reach your goal weight. ";
                }

                if (latestSugarLevel > 180.0) {
                    recommendationText += "Consider exercises that help manage blood sugar levels and consult a healthcare provider for your high sugar levels. ";
                } else if (latestSugarLevel < 70.0) {
                    recommendationText += "Consider including exercises that can help stabilize blood sugar levels. ";
                }

                if (age > 40 && targetPeriod < 6) {
                    recommendationText += "You might fail to achieve your goal. ";
                }
            } else {
                recommendationText = "You are on your way to achieve your goal";
            }
            String exerciseIntensity;

            // Check the insights to determine the exercise intensity level
            if (latestBloodPressure > 140.0 || latestWeight > goalWeight || latestSugarLevel > 180.0) {
                // If any of these conditions are met (high blood pressure, exceeding goal weight, high sugar level), set intensity to "high."
                exerciseIntensity = "High";
            } else if (latestBloodPressure < 90.0 || latestWeight < goalWeight || latestSugarLevel < 70.0) {
                // If any of these conditions are met (low blood pressure, below goal weight, low sugar level), set intensity to "low."
                exerciseIntensity = "Low";
            } else {
                // If none of the above conditions are met, set intensity to "medium."
                exerciseIntensity = "Medium";
            }
            List<Exercise> exercises= exerciseRepository.findByIntensity(exerciseIntensity);

            //Insert Data in Exercise Recommendation
            exerciseRecommendation.setDateTime(LocalDateTime.now());
            exerciseRecommendation.setUserId(userId);
            exerciseRecommendation.setSugarLevel(latestSugarLevel);
            exerciseRecommendation.setBloodPressure(latestBloodPressure);
            exerciseRecommendation.setAge(age);
            exerciseRecommendation.setWeight(latestWeight);
            exerciseRecommendation.setGoalWeight(goalWeight);
            exerciseRecommendation.setTargetPeriod(targetPeriod);
            exerciseRecommendation.copyDiseaseData(diseases);
            exerciseRecommendation.setMessage(recommendationText);
            exerciseRecommendation.setInsights(insights);
            exerciseRecommendation.setExerciseList(exercises);
            exerciseRecommendationRepository.save(exerciseRecommendation);

            // Create ExerciseRecommendationDto
            ExerciseRecommendationDto recommendationDto = new ExerciseRecommendationDto();
            recommendationDto.setInsights(insights);
            recommendationDto.setMessage(recommendationText);
            recommendationDto.setExerciseList(exercises);
            recommendationDto.setDate(LocalDateTime.now());
            return recommendationDto;
        }
        return null;
    }

    @Override
    public void addExerciseData(ExerciseRequestDto exerciseRequestDto) {
        Exercise exercise =new Exercise();
        exercise.setDescription(exerciseRequestDto.getDescription());
        exercise.setCategory(exerciseRequestDto.getCategory());
        exercise.setIntensity(exerciseRequestDto.getIntensity());
        exercise.setDuration(exerciseRequestDto.getDuration());
        exercise.setCalorieBurn(exerciseRequestDto.getCalorieBurn());
        exerciseRepository.save(exercise);
    }

    @Override
    public List<Exercise> getAllExercise() {
        return exerciseRepository.findAll();
    }

    @Override
    public List<ExerciseRecommendation> getAllExerciseRecommendations() {
        return exerciseRecommendationRepository.findAll();
    }

    @Override
    public ExerciseRecommendation getRecommendationById(UUID recommendationId) {
        return exerciseRecommendationRepository.findById(recommendationId).get();
    }
}
