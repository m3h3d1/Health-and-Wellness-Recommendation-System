package com.healthapp.recommendationserviceauto.repository;

import com.healthapp.recommendationserviceauto.domain.SleepRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SleepRecommendationRepository extends JpaRepository<SleepRecommendation, UUID> {
}
