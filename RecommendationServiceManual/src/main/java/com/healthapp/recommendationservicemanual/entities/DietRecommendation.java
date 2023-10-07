package com.healthapp.recommendationservicemanual.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// Entity class representing a Diet Recommendation
@Entity @Setter @Getter @RequiredArgsConstructor
public class DietRecommendation {
    @Id
    private UUID dietRecommendationId;
    private UUID userId;
    private String recommendationMessage;
    private LocalDateTime recommendationTime;

    // One-to-many relationship: Breakfast meals
    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> breakfast;

    // One-to-many relationship: Lunch meals
    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> lunch;

    // One-to-many relationship: Dinner meals
    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> dinner;

    // One-to-many relationship: Snacks
    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> snacks;

    private String healthNote;
    private UUID recommenderId;
}
