package com.healthapp.recommendationserviceauto.controller;

import com.healthapp.recommendationserviceauto.domain.Exercise;
import com.healthapp.recommendationserviceauto.domain.Health;
import com.healthapp.recommendationserviceauto.model.*;
import com.healthapp.recommendationserviceauto.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/health")
public class HealthController {
    @Autowired
    private HealthService healthService;
    @GetMapping("/{userId}")
    public ResponseEntity<Health> getHealthData(@PathVariable UUID userId){
        return new ResponseEntity<>(healthService.getHealthData(userId),HttpStatus.OK);
    }
    @PostMapping("/add/{userId}")
    public ResponseEntity<String> registerUser(@RequestBody HealthRequestDto healthRequestDto, @PathVariable UUID userId) {
        healthService.addHealthData(userId,healthRequestDto);
        return new ResponseEntity<>("Health data added!", HttpStatus.CREATED);
    }
    @PostMapping("/add-blood-pressure/{userId}")
    public ResponseEntity<String> addBloodPressure(@RequestBody BloodPressureRequestDto bloodPressureRequestDto, @PathVariable UUID userId) {
        healthService.addBloodPressureData(userId,bloodPressureRequestDto);
        return new ResponseEntity<>("Blood pressure added!", HttpStatus.CREATED);
    }
    @PostMapping("/add-sugar-level/{userId}")
    public ResponseEntity<String> addSugarLevel(@RequestBody SugarLevelDto sugarLevelDto, @PathVariable UUID userId) {
        healthService.addSugarLevelData(userId,sugarLevelDto);
        return new ResponseEntity<>("Sugar Level added!", HttpStatus.CREATED);
    }
    @PostMapping("/add-weight/{userId}")
    public ResponseEntity<String> addWeight(@RequestBody WeightRequestDto weightRequestDto, @PathVariable UUID userId) {
        healthService.addWeight(userId,weightRequestDto);
        return new ResponseEntity<>("Weight Data added!", HttpStatus.CREATED);
    }
    @PostMapping("/add-disease/{userId}")
    public ResponseEntity<String> addDisease(@RequestBody DiseaseRequestDto diseaseRequestDto, @PathVariable UUID userId) {
        healthService.addDisease(userId,diseaseRequestDto);
        return new ResponseEntity<>("Disease Data added!", HttpStatus.CREATED);
    }
}
