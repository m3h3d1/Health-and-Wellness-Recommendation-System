package com.healthapp.recommendationserviceauto.service.interfaces;

import com.healthapp.recommendationserviceauto.domain.Exercise;
import com.healthapp.recommendationserviceauto.domain.ExerciseRecommendation;
import com.healthapp.recommendationserviceauto.model.recommendationdto.ExerciseRecommendationDto;
import com.healthapp.recommendationserviceauto.model.entrydatadto.ExerciseRequestDto;

import java.util.List;
import java.util.UUID;

public interface ExerciseService {
    ExerciseRecommendationDto recommend(UUID userId);
    void addExerciseData(ExerciseRequestDto exerciseRequestDto);
    List<Exercise> getAllExercise();
    List<ExerciseRecommendation> getAllExerciseRecommendations();
    ExerciseRecommendation getRecommendationById(UUID recommendationId);
    boolean ifExists(UUID recommendationId);
}
