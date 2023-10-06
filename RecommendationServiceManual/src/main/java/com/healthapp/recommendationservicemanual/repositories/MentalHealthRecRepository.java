package com.healthapp.recommendationservicemanual.repositories;

import com.healthapp.recommendationservicemanual.entities.MentalHealthRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MentalHealthRecRepository extends JpaRepository<MentalHealthRecommendation, UUID> {
}
