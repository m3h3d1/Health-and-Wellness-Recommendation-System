package com.healthapp.recommendationservicemanual.service.interfaces;

import com.healthapp.recommendationservicemanual.entities.MentalHealthRecommendation;

import java.util.UUID;

public interface MentalHealthRecService {
    void create(MentalHealthRecommendation mentalHealthRecommendation);
    MentalHealthRecommendation readById(UUID recId);
    void update(MentalHealthRecommendation mentalHealthRecommendation);
    void delete(MentalHealthRecommendation mentalHealthRecommendation);
}
