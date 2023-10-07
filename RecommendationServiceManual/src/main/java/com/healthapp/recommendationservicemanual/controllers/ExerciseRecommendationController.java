package com.healthapp.recommendationservicemanual.controllers;

import com.healthapp.recommendationservicemanual.entities.ExerciseRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.service.interfaces.ExerciseRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * ExerciseRecommendationController is responsible for handling HTTP requests related to ExerciseRecommendation entities.
 */
@RestController
@RequestMapping("/exercise-recommendations")
public class ExerciseRecommendationController {

    private final ExerciseRecService exerciseRecService;

    /**
     * Constructs an ExerciseRecommendationController instance with the required dependencies.
     *
     * @param exerciseRecService The service for managing ExerciseRecommendation entities.
     */
    public ExerciseRecommendationController(ExerciseRecService exerciseRecService) {
        this.exerciseRecService = exerciseRecService;
    }

    /**
     * Creates a new ExerciseRecommendation entity.
     *
     * @param exerciseRecommendation The ExerciseRecommendation to create.
     * @return The created ExerciseRecommendation entity.
     */
    @PostMapping
    public ResponseEntity<?> createExerciseRecommendation(@RequestBody ExerciseRecommendation exerciseRecommendation) {
        exerciseRecService.create(exerciseRecommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseRecommendation);
    }

    /**
     * Retrieves an ExerciseRecommendation entity by its ID.
     *
     * @param recId The ID of the ExerciseRecommendation to retrieve.
     * @return The ExerciseRecommendation with the specified ID.
     * @throws EntityNotFoundException If no ExerciseRecommendation is found with the given ID.
     */
    @GetMapping("/{recId}")
    public ResponseEntity<?> getExerciseRecommendationById(@PathVariable UUID recId) {
        ExerciseRecommendation exerciseRecommendation = exerciseRecService.readById(recId);
        if (exerciseRecommendation == null) {
            throw new EntityNotFoundException("Exercise recommendation not found with ID: " + recId);
        }
        return ResponseEntity.ok(exerciseRecommendation);
    }

    /**
     * Updates an ExerciseRecommendation entity with the specified ID.
     *
     * @param recId             The ID of the ExerciseRecommendation to update.
     * @param updatedRecommendation The updated ExerciseRecommendation entity.
     * @return The updated ExerciseRecommendation entity.
     * @throws EntityNotFoundException If no ExerciseRecommendation is found with the given ID.
     */
    @PutMapping("/{recId}")
    public ResponseEntity<?> updateExerciseRecommendation(@PathVariable UUID recId, @RequestBody ExerciseRecommendation updatedRecommendation) {
        ExerciseRecommendation existingRecommendation = exerciseRecService.readById(recId);
        if (existingRecommendation == null) {
            throw new EntityNotFoundException("Exercise recommendation not found with ID: " + recId);
        }
        updatedRecommendation.setExerciseRecommendationId(recId);
        exerciseRecService.update(updatedRecommendation);
        return ResponseEntity.ok(updatedRecommendation);
    }

    /**
     * Deletes an ExerciseRecommendation entity with the specified ID.
     *
     * @param recId The ID of the ExerciseRecommendation to delete.
     * @return A response indicating the success of the deletion.
     * @throws EntityNotFoundException If no ExerciseRecommendation is found with the given ID.
     */
    @DeleteMapping("/{recId}")
    public ResponseEntity<?> deleteExerciseRecommendation(@PathVariable UUID recId) {
        ExerciseRecommendation exerciseRecommendation = exerciseRecService.readById(recId);
        if (exerciseRecommendation == null) {
            throw new EntityNotFoundException("Exercise recommendation not found with ID: " + recId);
        }
        exerciseRecService.delete(exerciseRecommendation);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
