package com.healthapp.recommendationserviceauto.service.interfaces;

import com.healthapp.recommendationserviceauto.domain.SleepRecommendation;
import com.healthapp.recommendationserviceauto.model.recommendationdto.SleepRecommendationDto;

import java.util.UUID;

public interface SleepService {
    SleepRecommendationDto recommend(UUID userId);
    SleepRecommendation getRecommendationById(UUID recommendationId);
    boolean ifExists(UUID recommendationId);
}
