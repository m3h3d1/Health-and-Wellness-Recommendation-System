package com.healthapp.feedbackprogress.controller;

import com.healthapp.feedbackprogress.dto.ProgressInsightDTO;
import com.healthapp.feedbackprogress.dto.healthdto.HealthDTO;
import com.healthapp.feedbackprogress.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/progress")
public class ProgressController {
    @Autowired
    ProgressService progressService;

    @GetMapping("/track/{userId}")
    public ResponseEntity<?> getProgressTrackById(@PathVariable UUID userId) {
        HealthDTO health = progressService.getProgressTrackById(userId);

        if (health != null) {
            return new ResponseEntity<>(health, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/insight/{userId}")
    public ResponseEntity<?> getProgressInsightById(@PathVariable UUID userId) {
        ProgressInsightDTO insight = progressService.getProgressInsightById(userId);

        if (insight != null) {
            return new ResponseEntity<>(insight, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
