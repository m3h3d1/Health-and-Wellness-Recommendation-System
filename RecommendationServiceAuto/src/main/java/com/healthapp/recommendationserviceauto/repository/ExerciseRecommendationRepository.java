package com.healthapp.recommendationserviceauto.repository;

import com.healthapp.recommendationserviceauto.domain.ExerciseRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRecommendationRepository extends JpaRepository<ExerciseRecommendation, UUID> {
}
