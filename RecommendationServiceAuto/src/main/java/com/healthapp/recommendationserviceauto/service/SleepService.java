package com.healthapp.recommendationserviceauto.service;

import com.healthapp.recommendationserviceauto.domain.ExerciseRecommendation;
import com.healthapp.recommendationserviceauto.domain.SleepRecommendation;
import com.healthapp.recommendationserviceauto.model.ExerciseRecommendationDto;
import com.healthapp.recommendationserviceauto.model.SleepRecommendationDto;

import java.util.UUID;

public interface SleepService {
    SleepRecommendationDto recommend(UUID userId);
    SleepRecommendation getRecommendationById(UUID recommendationId);
    boolean ifExists(UUID recommendationId);
}
