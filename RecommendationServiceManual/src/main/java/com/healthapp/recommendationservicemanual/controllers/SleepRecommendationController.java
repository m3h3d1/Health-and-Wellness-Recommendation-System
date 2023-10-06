package com.healthapp.recommendationservicemanual.controllers;

import com.healthapp.recommendationservicemanual.entities.SleepRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.service.interfaces.SleepRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sleep-recommendations")
public class SleepRecommendationController {

    private final SleepRecService sleepRecService;

    @Autowired
    public SleepRecommendationController(SleepRecService sleepRecService) {
        this.sleepRecService = sleepRecService;
    }

    @PostMapping
    public ResponseEntity<?> createSleepRecommendation(@RequestBody SleepRecommendation sleepRecommendation) {
        sleepRecService.create(sleepRecommendation);
        return ResponseEntity.status(HttpStatus.CREATED).body(sleepRecommendation);
    }

    @GetMapping("/{recId}")
    public ResponseEntity<?> getSleepRecommendationById(@PathVariable UUID recId) {
        SleepRecommendation sleepRecommendation = sleepRecService.readById(recId);
        if (sleepRecommendation == null) {
            throw new EntityNotFoundException("Sleep recommendation not found with ID: " + recId);
        }
        return ResponseEntity.ok(sleepRecommendation);
    }

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
