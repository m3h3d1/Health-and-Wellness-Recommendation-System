package com.healthapp.mentalhealthservice.repository;

import com.healthapp.mentalhealthservice.entity.MentalHealthExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MentalHealthExerciseRepository extends JpaRepository<MentalHealthExercise, UUID> {
    List<MentalHealthExercise> findByUserId(UUID userId);
}
