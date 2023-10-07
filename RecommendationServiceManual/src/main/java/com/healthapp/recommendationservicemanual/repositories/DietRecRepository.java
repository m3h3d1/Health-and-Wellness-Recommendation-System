package com.healthapp.recommendationservicemanual.repositories;

import com.healthapp.recommendationservicemanual.entities.DietRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DietRecRepository extends JpaRepository<DietRecommendation, UUID> {
}
