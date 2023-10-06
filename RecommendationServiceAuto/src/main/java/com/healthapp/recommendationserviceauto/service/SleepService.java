package com.healthapp.recommendationserviceauto.service;

import com.healthapp.recommendationserviceauto.model.ExerciseRecommendationDto;
import com.healthapp.recommendationserviceauto.model.SleepRecommendationDto;

import java.util.UUID;

public interface SleepService {
    SleepRecommendationDto recommend(UUID userId);
}
