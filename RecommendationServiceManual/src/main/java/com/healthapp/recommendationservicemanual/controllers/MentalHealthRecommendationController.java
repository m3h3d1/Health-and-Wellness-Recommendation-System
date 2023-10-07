package com.healthapp.recommendationservicemanual.controllers;

import com.healthapp.recommendationservicemanual.entities.MentalHealthRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.service.interfaces.MentalHealthRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * MentalHealthRecommendationController is responsible for handling HTTP requests related to MentalHealthRecommendation entities.
 */
@RestController
@RequestMapping("/mental-health-recommendations")
public class MentalHealthRecommendationController {

    private final MentalHealthRecService mentalHealthRecService;

    /**
     * Constructs a MentalHealthRecommendationController instance with the required dependencies.
     *
     * @param mentalHealthRecService The service for managing MentalHealthRecommendation entities.
     */
    public MentalHealthRecommendationController(MentalHealthRecService mentalHealthRecService) {
        this.mentalHealthRecService = mentalHealthRecService;
    }

    /**
     * Creates a new MentalHealthRecommendation entity.
     *
     * @param mentalHealthRecommendation The MentalHealthRecommendation to create.
     * @return The created MentalHealthRecommendation entity.
     */
    @PostMapping
    public ResponseEntity<?> createMentalHealthRecommendation(@RequestBody MentalHealthRecommendation mentalHealthRecommendation) {
        mentalHealthRecService.create(mentalHealthRecommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(mentalHealthRecommendation);
    }

    /**
     * Retrieves a MentalHealthRecommendation entity by its ID.
     *
     * @param recId The ID of the MentalHealthRecommendation to retrieve.
     * @return The MentalHealthRecommendation with the specified ID.
     * @throws EntityNotFoundException If no MentalHealthRecommendation is found with the given ID.
     */
    @GetMapping("/{recId}")
    public ResponseEntity<?> getMentalHealthRecommendationById(@PathVariable UUID recId) {
        MentalHealthRecommendation mentalHealthRecommendation = mentalHealthRecService.readById(recId);
        if (mentalHealthRecommendation == null) {
            throw new EntityNotFoundException("Mental health recommendation not found with ID: " + recId);
        }
        return ResponseEntity.ok(mentalHealthRecommendation);
    }

    /**
     * Updates a MentalHealthRecommendation entity with the specified ID.
     *
     * @param recId             The ID of the MentalHealthRecommendation to update.
     * @param updatedRecommendation The updated MentalHealthRecommendation entity.
     * @return The updated MentalHealthRecommendation entity.
     * @throws EntityNotFoundException If no MentalHealthRecommendation is found with the given ID.
     */
    @PutMapping("/{recId}")
    public ResponseEntity<?> updateMentalHealthRecommendation(@PathVariable UUID recId, @RequestBody MentalHealthRecommendation updatedRecommendation) {
        MentalHealthRecommendation existingRecommendation = mentalHealthRecService.readById(recId);
        if (existingRecommendation == null) {
            throw new EntityNotFoundException("Mental health recommendation not found with ID: " + recId);
        }
        updatedRecommendation.setMentalHealthRecId(recId);
        mentalHealthRecService.update(updatedRecommendation);
        return ResponseEntity.ok(updatedRecommendation);
    }

    /**
     * Deletes a MentalHealthRecommendation entity with the specified ID.
     *
     * @param recId The ID of the MentalHealthRecommendation to delete.
     * @return A response indicating the success of the deletion.
     * @throws EntityNotFoundException If no MentalHealthRecommendation is found with the given ID.
     */
    @DeleteMapping("/{recId}")
    public ResponseEntity<?> deleteMentalHealthRecommendation(@PathVariable UUID recId) {
        MentalHealthRecommendation mentalHealthRecommendation = mentalHealthRecService.readById(recId);
        if (mentalHealthRecommendation == null) {
            throw new EntityNotFoundException("Mental health recommendation not found with ID: " + recId);
        }
        mentalHealthRecService.delete(mentalHealthRecommendation);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
