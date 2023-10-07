package com.healthapp.recommendationservicemanual.service.implementations;

import com.healthapp.recommendationservicemanual.entities.ExerciseRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.DuplicateEntityException;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.exceptions.InternalServerErrorException;
import com.healthapp.recommendationservicemanual.networks.RecommendationAutoProxy;
import com.healthapp.recommendationservicemanual.repositories.ExerciseRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.ExerciseRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExerciseRecServiceImpl implements ExerciseRecService {
    private final ExerciseRecRepository exerciseRecRepository;
    private final RecommendationAutoProxy recommendationAutoProxy;

    @Autowired
    public ExerciseRecServiceImpl(ExerciseRecRepository exerciseRecRepository, RecommendationAutoProxy recommendationAutoProxy) {
        this.exerciseRecRepository = exerciseRecRepository;
        this.recommendationAutoProxy = recommendationAutoProxy;
    }

    @Override
    public void create(ExerciseRecommendation exerciseRecommendation) {
        ResponseEntity<Boolean> response = recommendationAutoProxy.ifExistsExerciseRec(exerciseRecommendation.getExerciseRecommendationId());
        if(response.getStatusCode() != HttpStatus.OK) {
            throw new InternalServerErrorException("Failed to communicate with auto recommendation service");
        }
        if(!response.getBody()) {
            throw new EntityNotFoundException("There is no automated recommendation record exists with given ID"
                    + exerciseRecommendation.getExerciseRecommendationId());
        }
        if(exerciseRecRepository.existsById(exerciseRecommendation.getExerciseRecommendationId())){
            throw new DuplicateEntityException("Can't create recommendation as the recommendation already exists, try to update!");
        }
        exerciseRecRepository.save(exerciseRecommendation);
    }

    @Override
    public ExerciseRecommendation readById(UUID recId) {
        return exerciseRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Exercise recommendation not found with ID: " + recId));
    }

    @Override
    public void update(ExerciseRecommendation exerciseRecommendation) {
        readById(exerciseRecommendation.getExerciseRecommendationId());
        exerciseRecRepository.save(exerciseRecommendation);
    }

    @Override
    public void delete(ExerciseRecommendation exerciseRecommendation) {
        readById(exerciseRecommendation.getExerciseRecommendationId());
        exerciseRecRepository.delete(exerciseRecommendation);
    }
}
