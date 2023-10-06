package com.healthapp.recommendationservicemanual.service.impl;

import com.healthapp.recommendationservicemanual.entities.DietRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.exceptions.InternalServerErrorException;
import com.healthapp.recommendationservicemanual.exceptions.ValidationException;
import com.healthapp.recommendationservicemanual.repositories.DietRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.DietRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DietRecServiceImpl implements DietRecService {
    private final DietRecRepository dietRecRepository;

    @Autowired
    public DietRecServiceImpl(DietRecRepository dietRecRepository) {
        this.dietRecRepository = dietRecRepository;
    }

    @Override
    public void create(DietRecommendation dietRecommendation) {
        try {
            dietRecRepository.save(dietRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to create diet recommendation.");
        }
    }

    @Override
    public DietRecommendation readById(UUID recId) {
        return dietRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Diet recommendation not found with ID: " + recId));
    }

    @Override
    public void update(DietRecommendation dietRecommendation) {
        try {
            dietRecRepository.save(dietRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to update diet recommendation.");
        }
    }

    @Override
    public void delete(DietRecommendation dietRecommendation) {
        try {
            dietRecRepository.delete(dietRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete diet recommendation.");
        }
    }
}
