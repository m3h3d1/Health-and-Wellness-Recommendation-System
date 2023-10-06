package com.healthapp.recommendationservicemanual.service.interfaces;

import com.healthapp.recommendationservicemanual.entities.SleepRecommendation;

import java.util.UUID;

public interface SleepRecService {
    void create(SleepRecommendation sleepRecommendation);
    SleepRecommendation readById(UUID recId);
    void update(SleepRecommendation sleepRecommendation);
    void delete(SleepRecommendation sleepRecommendation);
}
