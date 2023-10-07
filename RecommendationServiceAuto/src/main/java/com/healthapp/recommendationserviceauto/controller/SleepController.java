package com.healthapp.recommendationserviceauto.controller;

import com.healthapp.recommendationserviceauto.domain.DietRecommendation;
import com.healthapp.recommendationserviceauto.domain.SleepRecommendation;
import com.healthapp.recommendationserviceauto.model.ExerciseRecommendationDto;
import com.healthapp.recommendationserviceauto.model.SleepRecommendationDto;
import com.healthapp.recommendationserviceauto.service.SleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/health/sleep")
public class SleepController {
    @Autowired
    private SleepService sleepService;
    @GetMapping("/recommend/{userId}")
    public ResponseEntity<SleepRecommendationDto> getRecommendation(@PathVariable UUID userId){
        return new ResponseEntity<>(sleepService.recommend(userId), HttpStatus.OK);
    }
    @GetMapping("/{recommendId}")
    public ResponseEntity<SleepRecommendation> getRecommendationById(@PathVariable UUID recommendId){
        return new ResponseEntity<>(sleepService.getRecommendationById(recommendId), HttpStatus.OK);
    }
    @GetMapping("/recommend/if-exists/{recommendId}")
    public ResponseEntity<Boolean> checkIfRecordExists(@PathVariable UUID recommendId){
        return sleepService.ifExists(recommendId) ?
                new ResponseEntity<Boolean>(true, HttpStatus.OK) : new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
    }
}
