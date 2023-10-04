package com.healthapp.recommendationserviceauto.service;

import com.healthapp.recommendationserviceauto.model.ExerciseRecommendationDto;

import java.util.UUID;

public interface ExerciseService {
    ExerciseRecommendationDto recommend(UUID userId);
}
