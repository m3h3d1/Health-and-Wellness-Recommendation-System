package com.healthapp.recommendationservicemanual.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

// Entity class representing an Exercise
@Getter @Setter @Entity @RequiredArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID exerciseId;
    private String description;
    private String category;
    private String intensity;   // Intensity level of the exercise
    private Double duration;    // Duration of the exercise in minutes
    private Double calorieBurn; // Estimated calorie burn for the exercise
}

