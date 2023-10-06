package com.healthapp.dataanalysisservice2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.healthapp.dataanalysisservice2.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("/ml/analyze")
public class AnalyzeController {
    private final AnalyzeService analyzeService;

    public AnalyzeController(AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<String> analyzeData(@PathVariable UUID userId) throws JsonProcessingException {
        String analysisResult = analyzeService.AnalyzeData(userId);
        return ResponseEntity.ok(analysisResult);
    }
}
