package com.healthapp.mentalhealthservice.controller;

import com.healthapp.mentalhealthservice.dto.MentalHealthExerciseDTO;
import com.healthapp.mentalhealthservice.entity.MentalHealthExercise;
import com.healthapp.mentalhealthservice.exception.DataNotFindByUserIdException;
import com.healthapp.mentalhealthservice.service.MentalHealthExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mental-health/exercises")
public class MentalHealthExerciseController {

    private final MentalHealthExerciseService exerciseServiceImp;

    public MentalHealthExerciseController(MentalHealthExerciseService exerciseServiceImp) {
        this.exerciseServiceImp = exerciseServiceImp;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createMentalHealthExercise(@RequestBody MentalHealthExerciseDTO exerciseDTO) {
        MentalHealthExercise createdExercise = exerciseServiceImp.createMentalHealthExercise(exerciseDTO);
        if (createdExercise != null) {
            String successMessage = "MentalHealthExercise created successfully";
            return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
        } else {
            String errorMessage = "Failed to create MentalHealthExercise";
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<MentalHealthExercise>> getAllMentalHealthExercises() {
        List<MentalHealthExercise> exercises = exerciseServiceImp.getAllMentalHealthExercises();
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentalHealthExercise> getMentalHealthExerciseById(@PathVariable UUID id) {
        MentalHealthExercise exercise = exerciseServiceImp.getMentalHealthExerciseById(id);
        if (exercise != null) {
            return new ResponseEntity<>(exercise, HttpStatus.OK);
        } else {
            throw new DataNotFindByUserIdException("No MentalHealth Exercise By this userId");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MentalHealthExercise>> getMoodLogsByUserId(@PathVariable UUID userId) {
        List<MentalHealthExercise> exercises = exerciseServiceImp.getMentalHealthExerciseByUserId(userId);
        if (exercises != null) {
            return new ResponseEntity<>(exercises, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentalHealthExercise> updateMentalHealthExercise(@PathVariable UUID id, @RequestBody MentalHealthExerciseDTO exerciseDTO) {
        MentalHealthExercise updatedExercise = exerciseServiceImp.updateMentalHealthExercise(id, exerciseDTO);
        if (updatedExercise != null) {
            String successMessage = "MentalHealthExercise updated successfully";
            return new ResponseEntity<>(updatedExercise, HttpStatus.OK);
        } else {
            throw new DataNotFindByUserIdException("No MentalHealth Exercise By this userId");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMentalHealthExercise(@PathVariable UUID id) {
        boolean deleted = exerciseServiceImp.deleteMentalHealthExercise(id);
        if (deleted) {
            String successMessage = "MentalHealthExercise deleted successfully";
            return new ResponseEntity<>(successMessage, HttpStatus.NO_CONTENT);
        } else {
            throw new DataNotFindByUserIdException("No MentalHealth Exercise By this userId");
        }
    }
}
