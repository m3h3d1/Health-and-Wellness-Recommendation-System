package com.healthapp.recommendationserviceauto.service.impl;

import com.healthapp.recommendationserviceauto.domain.*;
import com.healthapp.recommendationserviceauto.model.ExerciseRecommendationDto;
import com.healthapp.recommendationserviceauto.model.SleepRecommendationDto;
import com.healthapp.recommendationserviceauto.repository.HealthRepository;
import com.healthapp.recommendationserviceauto.repository.SleepRecommendationRepository;
import com.healthapp.recommendationserviceauto.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class SleepServiceImpl implements SleepService {
    @Autowired
    private HealthRepository healthRepository;
    @Autowired
    private SleepRecommendationRepository sleepRecommendationRepository;
    @Override
    public SleepRecommendationDto recommend(UUID userId) {
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);
        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            List<Weight> weights = health.getWeights();
            List<Disease> diseases = health.getDiseases();
            Double latestWeight = weights.get(weights.size() - 1).getWeightInKg();
            Integer age = health.getAge();
            String gender = health.getGender();
            Boolean isSmoker = health.getSmokingStatus();

            // Initialize an empty insights string
            String insights = "";

            // Check weight and health
            if (latestWeight > 0) {
                insights += "Your latest recorded weight is " + latestWeight + " kg. ";
            }

            // Check age and health
            if (age > 0) {
                insights += "Your age is " + age + " years. ";
            }

            // Check gender and health
            if (gender != null && !gender.isEmpty()) {
                insights += "Your gender is " + gender + ". ";
            }

            // Check smoking status and health
            if (isSmoker != null) {
                if (isSmoker) {
                    insights += "You are a smoker. ";
                } else {
                    insights += "You are a non-smoker. ";
                }
            }
            String recommendationMessage="";
            if (!insights.isEmpty()) {
                // Example recommendation message based on insights
                recommendationMessage = "Based on your health insights, it is recommended to consider a sleep schedule provided below.";
            } else {
                // If no insights are available, provide a generic message
                recommendationMessage = "You are maintaining good health. Keep up the good work!";
            }
            Boolean hasDisease = true;
            if (diseases.isEmpty()) {
                hasDisease = false;
            }
            int recommendedSleepDuration = calculateSleepDuration(age, gender, isSmoker, hasDisease);
            LocalTime recommendedWakeTime = calculateRecommendedWakeTime(age, isSmoker);
            LocalTime recommendedBedTime = calculateRecommendedBedTime(recommendedWakeTime, recommendedSleepDuration);
            SleepRecommendation sleepRecommendation = new SleepRecommendation();
            sleepRecommendation.setAge(age);
            sleepRecommendation.setMessage(recommendationMessage);
            sleepRecommendation.setInsights(insights);
            sleepRecommendation.setGender(gender);
            sleepRecommendation.setWeight(latestWeight);
            sleepRecommendation.setDateTime(LocalDateTime.now());
            sleepRecommendation.copyDiseaseData(diseases);
            sleepRecommendation.setWakeTime(recommendedWakeTime);
            sleepRecommendation.setBedTime(recommendedBedTime);
            sleepRecommendationRepository.save(sleepRecommendation);

            //SleepRecommendation
            SleepRecommendationDto recommendationDto = new SleepRecommendationDto();
            recommendationDto.setInsights(insights);
            recommendationDto.setMessage(recommendationMessage);
            recommendationDto.setDuration(recommendedSleepDuration);
            recommendationDto.setDate(LocalDateTime.now());
            recommendationDto.setWakeTime(recommendedWakeTime);
            recommendationDto.setBedTime(recommendedBedTime);
            return recommendationDto;
        }
        return null;
    }

    @Override
    public SleepRecommendation getRecommendationById(UUID recommendationId) {
        return sleepRecommendationRepository.findById(recommendationId).get();
    }

    private int calculateSleepDuration(int age, String gender, boolean isSmoker, boolean hasDisease) {
        int recommendedSleepDuration = 8; // Default sleep duration

        // Adjust sleep duration based on age
        if (age >= 18 && age <= 25) {
            recommendedSleepDuration = 9; // Young adults (ages 18-25) may need more sleep
        } else if (age >= 65) {
            recommendedSleepDuration = 7; // Older adults (65+) may need slightly less sleep
        }

        // Adjust sleep duration based on gender
        if (gender.equals("Female")) {
            recommendedSleepDuration -= 1; // Women may need slightly less sleep
        }

        // Adjust sleep duration based on smoking status
        if (isSmoker) {
            recommendedSleepDuration -= 1; // Smokers may need slightly less sleep
        }

        // Adjust sleep duration based on disease
        if (hasDisease) {
            recommendedSleepDuration -= 1; // People with diseases may need slightly less sleep
        }

        return recommendedSleepDuration;
    }
    private static LocalTime calculateRecommendedWakeTime(int age, boolean isSmoker) {
        LocalTime recommendedWakeTime;

        // Calculate wake time based on age and smoking status
        if (isSmoker) {
            // Smokers generally need more sleep
            if (age >= 18 && age <= 25) {
                // Young adult smokers (ages 18-25)
                recommendedWakeTime = LocalTime.of(7, 30); // Wake up at 7:30 AM
            } else if (age >= 26 && age <= 64) {
                // Adult smokers (ages 26-64)
                recommendedWakeTime = LocalTime.of(8, 0); // Wake up at 8:00 AM
            } else {
                // Older adult smokers (65+)
                recommendedWakeTime = LocalTime.of(8, 30); // Wake up at 8:30 AM
            }
        } else {
            // Non-smokers
            if (age >= 18 && age <= 25) {
                // Young adults (ages 18-25)
                recommendedWakeTime = LocalTime.of(6, 0); // Wake up at 6:00 AM
            } else if (age >= 26 && age <= 64) {
                // Adults (ages 26-64)
                recommendedWakeTime = LocalTime.of(7, 0); // Wake up at 7:00 AM
            } else {
                // Older adults (65+)
                recommendedWakeTime = LocalTime.of(7, 30); // Wake up at 7:30 AM
            }
        }

        return recommendedWakeTime;
    }
    private static LocalTime calculateRecommendedBedTime(LocalTime recommendedWakeTime, int sleepDuration) {
        // Calculate bedtime based on wake time and sleep duration
        return recommendedWakeTime.minusHours(sleepDuration);
    }

    @Override
    public boolean ifExists(UUID recommendationId){
        return sleepRecommendationRepository.existsById(recommendationId);
    }

}
