package com.healthapp.recommendationservicemanual.service.impl;

import com.healthapp.recommendationservicemanual.entities.SleepRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.exceptions.InternalServerErrorException;
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
        try {
            sleepRecRepository.save(sleepRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to create sleep recommendation.");
        }
    }

    @Override
    public SleepRecommendation readById(UUID recId) {
        return sleepRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Sleep recommendation not found with ID: " + recId));
    }

    @Override
    public void update(SleepRecommendation sleepRecommendation) {
        try {
            sleepRecRepository.save(sleepRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to update sleep recommendation.");
        }
    }

    @Override
    public void delete(SleepRecommendation sleepRecommendation) {
        try {
            sleepRecRepository.delete(sleepRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete sleep recommendation.");
        }
    }
}
