package com.healthapp.recommendationservicemanual.service.impl;

import com.healthapp.recommendationservicemanual.entities.ExerciseRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.exceptions.InternalServerErrorException;
import com.healthapp.recommendationservicemanual.exceptions.ValidationException;
import com.healthapp.recommendationservicemanual.repositories.ExerciseRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.ExerciseRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExerciseRecServiceImpl implements ExerciseRecService {
    private final ExerciseRecRepository exerciseRecRepository;

    @Autowired
    public ExerciseRecServiceImpl(ExerciseRecRepository exerciseRecRepository) {
        this.exerciseRecRepository = exerciseRecRepository;
    }

    @Override
    public void create(ExerciseRecommendation exerciseRecommendation) {
        try {
            exerciseRecRepository.save(exerciseRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to create exercise recommendation.");
        }
    }

    @Override
    public ExerciseRecommendation readById(UUID recId) {
        return exerciseRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Exercise recommendation not found with ID: " + recId));
    }

    @Override
    public void update(ExerciseRecommendation exerciseRecommendation) {
        try {
            exerciseRecRepository.save(exerciseRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to update exercise recommendation.");
        }
    }

    @Override
    public void delete(ExerciseRecommendation exerciseRecommendation) {
        try {
            exerciseRecRepository.delete(exerciseRecommendation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Failed to delete exercise recommendation.");
        }
    }
}
