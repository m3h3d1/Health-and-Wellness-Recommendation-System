package com.healthapp.recommendationservicemanual.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Entity class representing an Exercise Recommendation
@Entity @Setter @Getter @RequiredArgsConstructor
public class ExerciseRecommendation {
    @Id
    private UUID exerciseRecommendationId;
    private UUID userId;                       // ID of the user receiving the recommendation
    private LocalDateTime recommendationTime; // Timestamp when the recommendation was made
    private String message;
    private String insights;                  // Additional insights or information

    // One-to-many relationship: List of recommended exercises
    @OneToMany(cascade = CascadeType.ALL)
    private List<Exercise> exerciseList;

    private String healthNote;                // Health-related notes or comments
    private UUID recommenderId;               // ID of the recommender (e.g., a doctor or fitness trainer)
}
