package com.healthapp.recommendationservicemanual.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity @Setter @Getter @RequiredArgsConstructor
public class DietRecommendation {
    @Id
    private UUID dietRecommendationId;
    private UUID userId;
    private String recommendationMessage;
    private LocalDateTime recommendationTime;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> breakfast;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> lunch;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> dinner;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Meal> snacks;

    private String healthNote;
    private UUID recommenderId;
}
