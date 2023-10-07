package com.healthapp.recommendationservicemanual.controllers;

import com.healthapp.recommendationservicemanual.entities.SleepRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.service.interfaces.SleepRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * SleepRecommendationController is responsible for handling HTTP requests related to SleepRecommendation entities.
 */
@RestController
@RequestMapping("/sleep-recommendations")
public class SleepRecommendationController {

    private final SleepRecService sleepRecService;

    /**
     * Constructs a SleepRecommendationController instance with the required dependencies.
     *
     * @param sleepRecService The service for managing SleepRecommendation entities.
     */
    @Autowired
    public SleepRecommendationController(SleepRecService sleepRecService) {
        this.sleepRecService = sleepRecService;
    }

    /**
     * Creates a new SleepRecommendation entity.
     *
     * @param sleepRecommendation The SleepRecommendation to create.
     * @return The created SleepRecommendation entity.
     */
    @PostMapping
    public ResponseEntity<?> createSleepRecommendation(@RequestBody SleepRecommendation sleepRecommendation) {
        sleepRecService.create(sleepRecommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(sleepRecommendation);
    }

    /**
     * Retrieves a SleepRecommendation entity by its ID.
     *
     * @param recId The ID of the SleepRecommendation to retrieve.
     * @return The SleepRecommendation with the specified ID.
     * @throws EntityNotFoundException If no SleepRecommendation is found with the given ID.
     */
    @GetMapping("/{recId}")
    public ResponseEntity<?> getSleepRecommendationById(@PathVariable UUID recId) {
        SleepRecommendation sleepRecommendation = sleepRecService.readById(recId);
        if (sleepRecommendation == null) {
            throw new EntityNotFoundException("Sleep recommendation not found with ID: " + recId);
        }
        return ResponseEntity.ok(sleepRecommendation);
    }

    /**
     * Updates a SleepRecommendation entity with the specified ID.
     *
     * @param recId             The ID of the SleepRecommendation to update.
     * @param updatedRecommendation The updated SleepRecommendation entity.
     * @return The updated SleepRecommendation entity.
     * @throws EntityNotFoundException If no SleepRecommendation is found with the given ID.
     */
    @PutMapping("/{recId}")
    public ResponseEntity<?> updateSleepRecommendation(@PathVariable UUID recId, @RequestBody SleepRecommendation updatedRecommendation) {
        SleepRecommendation existingRecommendation = sleepRecService.readById(recId);
        if (existingRecommendation == null) {
            throw new EntityNotFoundException("Sleep recommendation not found with ID: " + recId);
        }
        updatedRecommendation.setSleepRecommendationId(recId);
        sleepRecService.update(updatedRecommendation);
        return ResponseEntity.ok(updatedRecommendation);
    }

    /**
     * Deletes a SleepRecommendation entity with the specified ID.
     *
     * @param recId The ID of the SleepRecommendation to delete.
     * @return A response indicating the success of the deletion.
     * @throws EntityNotFoundException If no SleepRecommendation is found with the given ID.
     */
    @DeleteMapping("/{recId}")
    public ResponseEntity<?> deleteSleepRecommendation(@PathVariable UUID recId) {
        SleepRecommendation sleepRecommendation = sleepRecService.readById(recId);
        if (sleepRecommendation == null) {
            throw new EntityNotFoundException("Sleep recommendation not found with ID: " + recId);
        }
        sleepRecService.delete(sleepRecommendation);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
