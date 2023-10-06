package com.healthapp.dataanalysisservice2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.healthapp.dataanalysisservice2.service.PredictionsService;
import com.healthapp.dataanalysisservice2.service.UpdateRecommendationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/ml/update-recommendations")
public class UpdateRecommendationsController {
    private final UpdateRecommendationsService updateRecommendationsService;

    public UpdateRecommendationsController(UpdateRecommendationsService updateRecommendationsService) {
        this.updateRecommendationsService = updateRecommendationsService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> predictionsData(@PathVariable UUID userId) throws JsonProcessingException {

        String analysisResult = updateRecommendationsService.UpdateRecommendationsData(userId);
        if (analysisResult!=null) {
            String successMessage = "Data predictions completed successfully";
            return ResponseEntity.ok(successMessage + ": " + analysisResult);
        } else {
            String errorMessage = "Data not found for the specified user ID";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}

