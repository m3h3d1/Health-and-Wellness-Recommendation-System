package com.healthapp.mentalhealthservice.service;


import com.healthapp.mentalhealthservice.dto.MentalHealthExerciseDTO;
import com.healthapp.mentalhealthservice.entity.MentalHealthExercise;
import com.healthapp.mentalhealthservice.repository.MentalHealthExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class MentalHealthExerciseServiceImpl implements MentalHealthExerciseService {

    private final MentalHealthExerciseRepository exerciseRepository;

    @Autowired
    public MentalHealthExerciseServiceImpl(MentalHealthExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public MentalHealthExercise createMentalHealthExercise(MentalHealthExerciseDTO exerciseDTO) {
        MentalHealthExercise exercise = new MentalHealthExercise();
        // Set properties of exercise based on exerciseDTO
        exercise.setCategory(exerciseDTO.getCategory());
        exercise.setDescription(exerciseDTO.getDescription());
        exercise.setDuration(exerciseDTO.getDuration());
        exercise.setSuggestedForIssues(exerciseDTO.getSuggestedForIssues());
        exercise.setBenefits(exerciseDTO.getBenefits());
        exercise.setNotes(exerciseDTO.getNotes());
        return exerciseRepository.save(exercise);
    }

    @Override
    public MentalHealthExercise updateMentalHealthExercise(UUID id, MentalHealthExerciseDTO exerciseDTO) {
        Optional<MentalHealthExercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            MentalHealthExercise exercise = exerciseOptional.get();
            // Update properties of exercise based on exerciseDTO
            exercise.setCategory(exerciseDTO.getCategory());
            exercise.setDescription(exerciseDTO.getDescription());
            exercise.setDuration(exerciseDTO.getDuration());
            exercise.setSuggestedForIssues(exerciseDTO.getSuggestedForIssues());
            exercise.setBenefits(exerciseDTO.getBenefits());
            exercise.setNotes(exerciseDTO.getNotes());
            return exerciseRepository.save(exercise);
        }
        return null; // or throw an exception if mental health exercise with the given id is not found
    }

    @Override
    public MentalHealthExercise getMentalHealthExerciseById(UUID id) {
        Optional<MentalHealthExercise> exerciseOptional = exerciseRepository.findById(id);
        return exerciseOptional.orElse(null);
    }

    @Override
    public boolean deleteMentalHealthExercise(UUID id) {
        Optional<MentalHealthExercise> exerciseOptional = exerciseRepository.findById(id);
        if (exerciseOptional.isPresent()) {
            exerciseRepository.deleteById(id);
            return true; // Deletion was successful
        } else {
            return false; // MentalHealthExercise with the specified ID was not found
        }
    }

    @Override
    public List<MentalHealthExercise> getAllMentalHealthExercises() {
        return exerciseRepository.findAll();
    }
}

