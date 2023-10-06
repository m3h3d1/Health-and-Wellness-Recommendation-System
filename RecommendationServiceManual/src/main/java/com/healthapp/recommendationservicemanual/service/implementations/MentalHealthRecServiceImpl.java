package com.healthapp.recommendationservicemanual.service.impl;

import com.healthapp.recommendationservicemanual.entities.MentalHealthRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.exceptions.InternalServerErrorException;
import com.healthapp.recommendationservicemanual.repositories.MentalHealthRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.MentalHealthRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MentalHealthRecServiceImpl implements MentalHealthRecService {
    private final MentalHealthRecRepository mentalHealthRecRepository;

    @Autowired
    public MentalHealthRecServiceImpl(MentalHealthRecRepository mentalHealthRecRepository) {
        this.mentalHealthRecRepository = mentalHealthRecRepository;
    }

    @Override
    public void create(MentalHealthRecommendation mentalHealthRecommendation) {
        try {
            mentalHealthRecRepository.save(mentalHealthRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to create mental health recommendation.");
        }
    }

    @Override
    public MentalHealthRecommendation readById(UUID recId) {
        return mentalHealthRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Mental health recommendation not found with ID: " + recId));
    }

    @Override
    public void update(MentalHealthRecommendation mentalHealthRecommendation) {
        try {
            mentalHealthRecRepository.save(mentalHealthRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to update mental health recommendation.");
        }
    }

    @Override
    public void delete(MentalHealthRecommendation mentalHealthRecommendation) {
        try {
            mentalHealthRecRepository.delete(mentalHealthRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete mental health recommendation.");
        }
    }
}
