package com.healthapp.recommendationserviceauto.service.interfaces;

import com.healthapp.recommendationserviceauto.domain.Health;
import com.healthapp.recommendationserviceauto.model.entrydatadto.ActivityRequestDto;
import com.healthapp.recommendationserviceauto.model.requestdto.*;

import java.util.UUID;

public interface HealthService {
    Health getHealthData(UUID userId);
    void addHealthData(UUID userId, HealthRequestDto healthRequestDto);
    void addBloodPressureData(UUID userId, BloodPressureRequestDto bloodPressureRequestDto);
    void addSugarLevelData(UUID userId, SugarLevelDto sugarLevelDto);
    void addWeight(UUID userID, WeightRequestDto weightRequestDto);
    void addHeight(UUID userID, HeightRequestDto heightRequestDto);
    void addDisease(UUID userID, DiseaseRequestDto diseaseRequestDto);
    void addActivityData(ActivityRequestDto activityRequestDto);
    void addExtraData(UUID userId, ExtraRequestDto smokerAllergyRequestDto);
    void updateHealthData(UUID userId, HealthUpdateRequestDto healthUpdateRequestDto);
}
