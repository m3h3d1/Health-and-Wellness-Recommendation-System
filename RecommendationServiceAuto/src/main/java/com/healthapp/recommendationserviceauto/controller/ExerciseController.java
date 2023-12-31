package com.healthapp.recommendationserviceauto.controller;

import com.healthapp.recommendationserviceauto.domain.Exercise;
import com.healthapp.recommendationserviceauto.domain.ExerciseRecommendation;
import com.healthapp.recommendationserviceauto.model.recommendationdto.ExerciseRecommendationDto;
import com.healthapp.recommendationserviceauto.model.entrydatadto.ExerciseRequestDto;
import com.healthapp.recommendationserviceauto.service.interfaces.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/health/exercise")
public class ExerciseController {
    @Autowired
    private ExerciseService exerciseService;
    @PostMapping("/add")
    public ResponseEntity<String> registerUser(@RequestBody ExerciseRequestDto exerciseRequestDto) {
        exerciseService.addExerciseData(exerciseRequestDto);
        return new ResponseEntity<>("Exercise data added!", HttpStatus.CREATED);
    }
    @GetMapping("/recommend/{userId}")
    public ResponseEntity<ExerciseRecommendationDto> getRecommendation(@PathVariable UUID userId){
        return new ResponseEntity<>(exerciseService.recommend(userId),HttpStatus.OK);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<Exercise>> getAllExercise(){
        return new ResponseEntity<>(exerciseService.getAllExercise(),HttpStatus.OK);
    }
    @GetMapping("/get-all-recommendation")
    public ResponseEntity<List<ExerciseRecommendation>> getAllExerciseRecommendations(){
        return new ResponseEntity<>(exerciseService.getAllExerciseRecommendations(),HttpStatus.OK);
    }
    @GetMapping("/{recommendId}")
    public ResponseEntity<ExerciseRecommendation> getRecommendationById(@PathVariable UUID recommendId){
        return new ResponseEntity<>(exerciseService.getRecommendationById(recommendId), HttpStatus.OK);
    }
    @GetMapping("/recommend/if-exists/{recommendId}")
    public ResponseEntity<Boolean> checkIfRecordExists(@PathVariable UUID recommendId){
        return new ResponseEntity<Boolean>(exerciseService.ifExists(recommendId), HttpStatus.OK);
    }
}
