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

/**
 * ExerciseRecServiceImpl is the implementation of the ExerciseRecService interface for managing ExerciseRecommendation entities.
 */
@Service
public class ExerciseRecServiceImpl implements ExerciseRecService {
    private final ExerciseRecRepository exerciseRecRepository;
    private final RecommendationAutoProxy recommendationAutoProxy;

    @Autowired
    public ExerciseRecServiceImpl(ExerciseRecRepository exerciseRecRepository, RecommendationAutoProxy recommendationAutoProxy) {
        this.exerciseRecRepository = exerciseRecRepository;
        this.recommendationAutoProxy = recommendationAutoProxy;
    }

    /**
     * Creates a new ExerciseRecommendation entity.
     *
     * @param exerciseRecommendation The ExerciseRecommendation to create.
     * @throws InternalServerErrorException If there is an issue communicating with the auto recommendation service.
     * @throws EntityNotFoundException     If no automated recommendation record exists with the given ID.
     * @throws DuplicateEntityException     If a recommendation with the same ID already exists.
     */
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

    /**
     * Reads an ExerciseRecommendation entity by its ID.
     *
     * @param recId The ID of the ExerciseRecommendation to read.
     * @return The ExerciseRecommendation with the specified ID.
     * @throws EntityNotFoundException If no ExerciseRecommendation is found with the given ID.
     */
    @Override
    public ExerciseRecommendation readById(UUID recId) {
        return exerciseRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Exercise recommendation not found with ID: " + recId));
    }

    /**
     * Updates an ExerciseRecommendation entity.
     *
     * @param exerciseRecommendation The ExerciseRecommendation to update.
     * @throws EntityNotFoundException If no ExerciseRecommendation is found with the ID of the provided ExerciseRecommendation.
     */
    @Override
    public void update(ExerciseRecommendation exerciseRecommendation) {
        readById(exerciseRecommendation.getExerciseRecommendationId());
        exerciseRecRepository.save(exerciseRecommendation);
    }

    /**
     * Deletes an ExerciseRecommendation entity.
     *
     * @param exerciseRecommendation The ExerciseRecommendation to to delete.
     * @throws EntityNotFoundException If no ExerciseRecommendation is found with the ID of the provided ExerciseRecommendation.
     */
    @Override
    public void delete(ExerciseRecommendation exerciseRecommendation) {
        readById(exerciseRecommendation.getExerciseRecommendationId());
        exerciseRecRepository.delete(exerciseRecommendation);
    }
}
