package com.healthapp.recommendationservicemanual.service.interfaces;

import com.healthapp.recommendationservicemanual.entities.ExerciseRecommendation;

import java.util.UUID;

public interface ExerciseRecService {
    void create(ExerciseRecommendation exerciseRecommendation);
    ExerciseRecommendation readById(UUID recId);
    void update(ExerciseRecommendation exerciseRecommendation);
    void delete(ExerciseRecommendation exerciseRecommendation);
}
