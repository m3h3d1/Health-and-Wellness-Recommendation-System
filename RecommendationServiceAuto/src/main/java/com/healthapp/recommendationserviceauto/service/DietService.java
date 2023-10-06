package com.healthapp.recommendationserviceauto.service;

import com.healthapp.recommendationserviceauto.model.DietRecommendationDto;
import com.healthapp.recommendationserviceauto.model.SleepRecommendationDto;

import java.util.UUID;

public interface DietService {
    DietRecommendationDto recommend(UUID userId);
}
