package com.healthapp.recommendationservicemanual.service.impl;

import com.healthapp.recommendationservicemanual.entities.SleepRecommendation;
import com.healthapp.recommendationservicemanual.repositories.SleepRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.SleepRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SleepRecServiceImpl implements SleepRecService {
    private final SleepRecRepository sleepRecRepository;

    @Autowired
    public SleepRecServiceImpl(SleepRecRepository sleepRecRepository) {
        this.sleepRecRepository = sleepRecRepository;
    }

    @Override
    public void create(SleepRecommendation sleepRecommendation) {
        sleepRecRepository.save(sleepRecommendation);
    }

    @Override
    public SleepRecommendation readById(UUID recId) {
        return sleepRecRepository.findById(recId).orElse(null);
    }

    @Override
    public void update(SleepRecommendation sleepRecommendation) {
        sleepRecRepository.save(sleepRecommendation);
    }

    @Override
    public void delete(SleepRecommendation sleepRecommendation) {
        sleepRecRepository.delete(sleepRecommendation);
    }
}
