package com.healthapp.mentalhealthservice.service;

import com.healthapp.mentalhealthservice.dto.MentalHealthExerciseDTO;
import com.healthapp.mentalhealthservice.entity.MentalHealthExercise;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface MentalHealthExerciseService {
     MentalHealthExercise createMentalHealthExercise(MentalHealthExerciseDTO exerciseDTO);
     MentalHealthExercise updateMentalHealthExercise(UUID id, MentalHealthExerciseDTO exerciseDTO);
     MentalHealthExercise getMentalHealthExerciseById(UUID id);
     boolean  deleteMentalHealthExercise(UUID id);
     List<MentalHealthExercise> getAllMentalHealthExercises();
}

