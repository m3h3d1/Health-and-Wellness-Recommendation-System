package com.healthapp.recommendationserviceauto.service;

import com.healthapp.recommendationserviceauto.model.BloodPressureRequestDto;
import com.healthapp.recommendationserviceauto.model.HealthRequestDto;

import java.util.UUID;

public interface HealthService {
    void addHealthData(UUID userId, HealthRequestDto healthRequestDto);
    void addBloodPressureData(UUID userId, BloodPressureRequestDto bloodPressureRequestDto);
}
