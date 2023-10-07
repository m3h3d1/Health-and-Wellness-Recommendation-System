package com.healthapp.recommendationserviceauto.controller;

import com.healthapp.recommendationserviceauto.domain.DietRecommendation;
import com.healthapp.recommendationserviceauto.model.DietRecommendationDto;
import com.healthapp.recommendationserviceauto.model.SleepRecommendationDto;
import com.healthapp.recommendationserviceauto.service.DietService;
import com.healthapp.recommendationserviceauto.service.SleepService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/health/diet")
public class DietController {
    @Autowired
    private DietService dietService;
    @GetMapping("/recommend/{userId}")
    public ResponseEntity<DietRecommendationDto> getRecommendation(@PathVariable UUID userId){
        return new ResponseEntity<>(dietService.recommend(userId), HttpStatus.OK);
    }
    @GetMapping("/{recommendId}")
    public ResponseEntity<DietRecommendation> getRecommendationById(@PathVariable UUID recommendId){
        return new ResponseEntity<>(dietService.getRecommendationById(recommendId), HttpStatus.OK);
    }
    @GetMapping("/recommend/if-exists/{recommendId}")
    public ResponseEntity<Boolean> checkIfRecordExists(@PathVariable UUID recommendId){
        return dietService.ifExists(recommendId) ?
                new ResponseEntity<Boolean>(true, HttpStatus.OK) : new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
    }
}
