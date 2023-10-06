package com.healthapp.dataanalysisservice2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.healthapp.dataanalysisservice2.service.AnalyzeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/ml/analyze")
public class MalyzeController {
    private final AnalyzeService analyzeService;

    public MalyzeController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> analyzeData(@PathVariable UUID userId) throws JsonProcessingException {

        String analysisResult = analyzeService.AnalyzeData(userId);
        if (analysisResult!=null) {
            String successMessage = "Data analysis completed successfully";
            return ResponseEntity.ok(successMessage + ": " + analysisResult);
        } else {
            String errorMessage = "Data not found for the specified user ID";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }
}

