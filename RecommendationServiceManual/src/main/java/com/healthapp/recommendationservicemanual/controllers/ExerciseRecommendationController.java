package com.healthapp.recommendationservicemanual.controllers;

import com.healthapp.recommendationservicemanual.entities.ExerciseRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.service.interfaces.ExerciseRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/exercise-recommendations")
public class ExerciseRecommendationController {

    private final ExerciseRecService exerciseRecService;

    @Autowired
    public ExerciseRecommendationController(ExerciseRecService exerciseRecService) {
        this.exerciseRecService = exerciseRecService;
    }

    @PostMapping
    public ResponseEntity<?> createExerciseRecommendation(@RequestBody ExerciseRecommendation exerciseRecommendation) {
        exerciseRecService.create(exerciseRecommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(exerciseRecommendation);
    }

    @GetMapping("/{recId}")
    public ResponseEntity<?> getExerciseRecommendationById(@PathVariable UUID recId) {
        ExerciseRecommendation exerciseRecommendation = exerciseRecService.readById(recId);
        if (exerciseRecommendation == null) {
            throw new EntityNotFoundException("Exercise recommendation not found with ID: " + recId);
        }
        return ResponseEntity.ok(exerciseRecommendation);
    }

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
