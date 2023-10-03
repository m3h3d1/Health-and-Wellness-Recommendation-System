package com.healthapp.recommendationserviceauto.repository;

import com.healthapp.recommendationserviceauto.domain.Insights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface InsightsRepository extends JpaRepository<Insights, UUID> {
}
