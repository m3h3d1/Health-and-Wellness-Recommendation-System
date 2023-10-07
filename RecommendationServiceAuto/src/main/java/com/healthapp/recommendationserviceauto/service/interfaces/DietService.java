package com.healthapp.recommendationserviceauto.service.interfaces;

import com.healthapp.recommendationserviceauto.domain.DietRecommendation;
import com.healthapp.recommendationserviceauto.model.recommendationdto.DietRecommendationDto;

import java.util.UUID;

public interface DietService {
    DietRecommendationDto recommend(UUID userId);
    DietRecommendation getRecommendationById(UUID recommendationId);
    boolean ifExists(UUID recommendationId);
}
