package com.healthapp.recommendationservicemanual.repositories;

import com.healthapp.recommendationservicemanual.entities.ExerciseRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRecRepository extends JpaRepository<ExerciseRecommendation, UUID> {
}
