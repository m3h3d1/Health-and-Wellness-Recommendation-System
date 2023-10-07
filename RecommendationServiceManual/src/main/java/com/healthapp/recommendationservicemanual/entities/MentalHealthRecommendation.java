package com.healthapp.recommendationservicemanual.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

// Entity class representing a Mental Health Recommendation
@Entity @Setter @Getter @RequiredArgsConstructor
public class MentalHealthRecommendation {
    @Id
    private UUID mentalHealthRecId;
    String description;             // Description or message for the recommendation
}
