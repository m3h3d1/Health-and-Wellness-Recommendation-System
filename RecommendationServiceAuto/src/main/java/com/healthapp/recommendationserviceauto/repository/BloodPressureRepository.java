package com.healthapp.recommendationserviceauto.repository;

import com.healthapp.recommendationserviceauto.domain.BloodPressure;
import com.healthapp.recommendationserviceauto.domain.Health;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface BloodPressureRepository extends JpaRepository<BloodPressure, UUID> {
    List<BloodPressure> findByHealth(Health health);
}
