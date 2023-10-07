package com.healthapp.recommendationservicemanual.service.interfaces;

import com.healthapp.recommendationservicemanual.entities.DietRecommendation;

import java.util.UUID;

public interface DietRecService {
    void create(DietRecommendation dietRecommendation);
    DietRecommendation readById(UUID recId);
    void update(DietRecommendation dietRecommendation);
    void delete(DietRecommendation dietRecommendation);
}
