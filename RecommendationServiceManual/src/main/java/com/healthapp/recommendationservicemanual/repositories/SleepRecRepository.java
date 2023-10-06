package com.healthapp.recommendationservicemanual.repositories;

import com.healthapp.recommendationservicemanual.entities.SleepRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SleepRecRepository extends JpaRepository<SleepRecommendation, UUID> {
}
