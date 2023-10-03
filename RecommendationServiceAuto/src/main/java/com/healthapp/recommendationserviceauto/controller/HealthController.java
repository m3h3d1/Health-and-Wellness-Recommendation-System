package com.healthapp.recommendationserviceauto.controller;

import com.healthapp.recommendationserviceauto.model.BloodPressureRequestDto;
import com.healthapp.recommendationserviceauto.model.HealthRequestDto;
import com.healthapp.recommendationserviceauto.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/health")
public class HealthController {
    @Autowired
    private HealthService healthService;
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
}
