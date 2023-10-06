package com.healthapp.recommendationservicemanual.service.impl;

import com.healthapp.recommendationservicemanual.entities.ExerciseRecommendation;
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
        exerciseRecRepository.save(exerciseRecommendation);
    }

    @Override
    public ExerciseRecommendation readById(UUID recId) {
        return exerciseRecRepository.findById(recId).orElse(null);
    }

    @Override
    public void update(ExerciseRecommendation exerciseRecommendation) {
        exerciseRecRepository.save(exerciseRecommendation);
    }

    @Override
    public void delete(ExerciseRecommendation exerciseRecommendation) {
        exerciseRecRepository.delete(exerciseRecommendation);
    }
}
