package com.healthapp.recommendationservicemanual.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
// Entity class representing a Sleep Recommendation
@Entity @Setter @Getter @RequiredArgsConstructor
public class SleepRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sleepRecommendationId;
    private UUID userId;                   // ID of the user receiving the recommendation
    private LocalDateTime recommendationTime;

    private UUID autoDietRecommendationId;  // ID of the associated automatic diet recommendation
    private String healthNote;              // Health-related notes or comments
    private UUID recommenderId;             // ID of the recommender (e.g., a doctor)
}

