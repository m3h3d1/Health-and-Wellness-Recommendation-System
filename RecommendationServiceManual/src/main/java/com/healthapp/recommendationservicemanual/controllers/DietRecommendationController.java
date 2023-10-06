package com.healthapp.recommendationservicemanual.controllers;

import com.healthapp.recommendationservicemanual.entities.DietRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.service.interfaces.DietRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/diet-recommendations")
public class DietRecommendationController {

    private final DietRecService dietRecService;

    @Autowired
    public DietRecommendationController(DietRecService dietRecService) {
        this.dietRecService = dietRecService;
    }

    @PostMapping
    public ResponseEntity<?> createDietRecommendation(@RequestBody DietRecommendation dietRecommendation) {
        dietRecService.create(dietRecommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(dietRecommendation);
    }

    @GetMapping("/{recId}")
    public ResponseEntity<?> getDietRecommendationById(@PathVariable UUID recId) {
        DietRecommendation dietRecommendation = dietRecService.readById(recId);
        if (dietRecommendation == null) {
            throw new EntityNotFoundException("Diet recommendation not found with ID: " + recId);
        }
        return ResponseEntity.ok(dietRecommendation);
    }

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
