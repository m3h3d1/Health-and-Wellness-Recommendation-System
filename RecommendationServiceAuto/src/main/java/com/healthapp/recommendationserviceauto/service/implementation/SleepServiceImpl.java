package com.healthapp.recommendationserviceauto.service.implementation;

import com.healthapp.recommendationserviceauto.domain.*;
import com.healthapp.recommendationserviceauto.exception.EmptyResultException;
import com.healthapp.recommendationserviceauto.model.recommendationdto.SleepRecommendationDto;
import com.healthapp.recommendationserviceauto.repository.HealthRepository;
import com.healthapp.recommendationserviceauto.repository.SleepRecommendationRepository;
import com.healthapp.recommendationserviceauto.service.interfaces.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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

            // Sort the weights by DateTime
            Collections.sort(weights, Comparator.comparing(Weight::getDateTime).reversed());

            // Retrieve the latest weight
            Double latestWeight = weights.isEmpty() ? null : weights.get(0).getWeightInKg();

            Integer age = health.getAge();
            String gender = health.getGender();
            Boolean isSmoker = health.getSmokingStatus();
            Boolean hasDisease = true;
            if (diseases.isEmpty()) {
                hasDisease = false;
            }
            String insights = generateInsights(latestWeight, age, gender, isSmoker, diseases);
            String recommendationMessage = generateRecommendationMessage(insights);
            int recommendedSleepDuration = calculateSleepDuration(age, gender, isSmoker, hasDisease);
            LocalTime recommendedWakeTime = calculateRecommendedWakeTime(age, isSmoker);
            LocalTime recommendedBedTime = calculateRecommendedBedTime(recommendedWakeTime, recommendedSleepDuration);
            SleepRecommendation sleepRecommendation = createAndSaveSleepRecommendation(latestWeight, age, gender, isSmoker, diseases, insights, recommendationMessage, recommendedSleepDuration, recommendedWakeTime, recommendedBedTime);
            SleepRecommendationDto recommendationDto = createSleepRecommendationDto(sleepRecommendation);
            return recommendationDto;
        }
        throw new EmptyResultException();
    }
    private String generateInsights(Double latestWeight, Integer age, String gender, Boolean isSmoker, List<Disease> diseases) {
        StringBuilder insights = new StringBuilder();

        if (latestWeight > 0) {
            insights.append("Your latest recorded weight is ").append(latestWeight).append(" kg. ");
        }

        if (age > 0) {
            insights.append("Your age is ").append(age).append(" years. ");
        }

        if (gender != null && !gender.isEmpty()) {
            insights.append("Your gender is ").append(gender).append(". ");
        }

        if (isSmoker != null) {
            if (isSmoker) {
                insights.append("You are a smoker. ");
            } else {
                insights.append("You are a non-smoker. ");
            }
        }

        if (!diseases.isEmpty()) {
            insights.append("You have the following diseases: ");
            for (Disease disease : diseases) {
                insights.append(disease.getDiseaseName()).append(", ");
            }
            insights.setLength(insights.length() - 2); // Remove the trailing comma and space
            insights.append(". ");
        }

        return insights.toString();
    }

    private String generateRecommendationMessage(String insights) {
        if (!insights.isEmpty()) {
            return "Based on your health insights, it is recommended to consider a sleep schedule provided below.";
        } else {
            return "You are maintaining good health. Keep up the good work!";
        }
    }

    private SleepRecommendation createAndSaveSleepRecommendation(Double latestWeight, Integer age, String gender, Boolean isSmoker, List<Disease> diseases, String insights, String recommendationMessage, int recommendedSleepDuration, LocalTime recommendedWakeTime, LocalTime recommendedBedTime) {
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
        return sleepRecommendation;
    }

    private SleepRecommendationDto createSleepRecommendationDto(SleepRecommendation sleepRecommendation) {
        SleepRecommendationDto recommendationDto = new SleepRecommendationDto();
        recommendationDto.setInsights(sleepRecommendation.getInsights());
        recommendationDto.setMessage(sleepRecommendation.getMessage());
        recommendationDto.setDuration(sleepRecommendation.getDuration());
        recommendationDto.setDate(LocalDateTime.now());
        recommendationDto.setWakeTime(sleepRecommendation.getWakeTime());
        recommendationDto.setBedTime(sleepRecommendation.getBedTime());
        return recommendationDto;
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
