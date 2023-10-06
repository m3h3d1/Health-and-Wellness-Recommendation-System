package com.healthapp.recommendationservicemanual.controllers;

import com.healthapp.recommendationservicemanual.entities.MentalHealthRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.service.interfaces.MentalHealthRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/mental-health-recommendations")
public class MentalHealthRecommendationController {

    private final MentalHealthRecService mentalHealthRecService;

    @Autowired
    public MentalHealthRecommendationController(MentalHealthRecService mentalHealthRecService) {
        this.mentalHealthRecService = mentalHealthRecService;
    }

    @PostMapping
    public ResponseEntity<?> createMentalHealthRecommendation(@RequestBody MentalHealthRecommendation mentalHealthRecommendation) {
        mentalHealthRecService.create(mentalHealthRecommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(mentalHealthRecommendation);
    }

    @GetMapping("/{recId}")
    public ResponseEntity<?> getMentalHealthRecommendationById(@PathVariable UUID recId) {
        MentalHealthRecommendation mentalHealthRecommendation = mentalHealthRecService.readById(recId);
        if (mentalHealthRecommendation == null) {
            throw new EntityNotFoundException("Mental health recommendation not found with ID: " + recId);
        }
        return ResponseEntity.ok(mentalHealthRecommendation);
    }

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
