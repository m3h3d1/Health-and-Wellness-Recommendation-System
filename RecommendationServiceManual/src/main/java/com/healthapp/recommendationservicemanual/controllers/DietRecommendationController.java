package com.healthapp.recommendationservicemanual.controllers;

import com.healthapp.recommendationservicemanual.entities.DietRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.service.interfaces.DietRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * DietRecommendationController is responsible for handling HTTP requests related to DietRecommendation entities.
 */
@RestController
@RequestMapping("/diet-recommendations")
public class DietRecommendationController {

    private final DietRecService dietRecService;

    /**
     * Constructs a DietRecommendationController instance with the required dependencies.
     *
     * @param dietRecService The service for managing DietRecommendation entities.
     */

    public DietRecommendationController(DietRecService dietRecService) {
        this.dietRecService = dietRecService;
    }

    /**
     * Creates a new DietRecommendation entity.
     *
     * @param dietRecommendation The DietRecommendation to create.
     * @return The created DietRecommendation entity.
     */
    @PostMapping
    public ResponseEntity<?> createDietRecommendation(@RequestBody DietRecommendation dietRecommendation) {
        dietRecService.create(dietRecommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(dietRecommendation);
    }

    /**
     * Retrieves a DietRecommendation entity by its ID.
     *
     * @param recId The ID of the DietRecommendation to retrieve.
     * @return The DietRecommendation with the specified ID.
     * @throws EntityNotFoundException If no DietRecommendation is found with the given ID.
     */
    @GetMapping("/{recId}")
    public ResponseEntity<?> getDietRecommendationById(@PathVariable UUID recId) {
        DietRecommendation dietRecommendation = dietRecService.readById(recId);
        if (dietRecommendation == null) {
            throw new EntityNotFoundException("Diet recommendation not found with ID: " + recId);
        }
        return ResponseEntity.ok(dietRecommendation);
    }

    /**
     * Updates a DietRecommendation entity with the specified ID.
     *
     * @param recId             The ID of the DietRecommendation to update.
     * @param updatedRecommendation The updated DietRecommendation entity.
     * @return The updated DietRecommendation entity.
     * @throws EntityNotFoundException If no DietRecommendation is found with the given ID.
     */
    @PutMapping("/{recId}")
    public ResponseEntity<?> updateDietRecommendation(@PathVariable UUID recId, @RequestBody DietRecommendation updatedRecommendation) {
        DietRecommendation existingRecommendation = dietRecService.readById(recId);
        if (existingRecommendation == null) {
            throw new EntityNotFoundException("Diet recommendation not found with ID: " + recId);
        }
        updatedRecommendation.setDietRecommendationId(recId);
        dietRecService.update(updatedRecommendation);
        return ResponseEntity.ok(updatedRecommendation);
    }

    /**
     * Deletes a DietRecommendation entity with the specified ID.
     *
     * @param recId The ID of the DietRecommendation to delete.
     * @return A response indicating the success of the deletion.
     * @throws EntityNotFoundException If no DietRecommendation is found with the given ID.
     */
    @DeleteMapping("/{recId}")
    public ResponseEntity<?> deleteDietRecommendation(@PathVariable UUID recId) {
        DietRecommendation dietRecommendation = dietRecService.readById(recId);
        if (dietRecommendation == null) {
            throw new EntityNotFoundException("Diet recommendation not found with ID: " + recId);
        }
        dietRecService.delete(dietRecommendation);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
