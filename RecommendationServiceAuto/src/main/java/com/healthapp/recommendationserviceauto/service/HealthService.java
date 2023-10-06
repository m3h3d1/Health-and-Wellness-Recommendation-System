package com.healthapp.recommendationserviceauto.service;

import com.healthapp.recommendationserviceauto.model.*;

import java.util.UUID;

public interface HealthService {
    void addHealthData(UUID userId, HealthRequestDto healthRequestDto);
    void addBloodPressureData(UUID userId, BloodPressureRequestDto bloodPressureRequestDto);
    void addSugarLevelData(UUID userId, SugarLevelDto sugarLevelDto);
    void addWeight(UUID userID, WeightRequestDto weightRequestDto);
    void addDisease(UUID userID, DiseaseRequestDto diseaseRequestDto);
    void addSmokerAllergyData(UUID userId, SmokerAllergyRequestDto smokerAllergyRequestDto);
}
