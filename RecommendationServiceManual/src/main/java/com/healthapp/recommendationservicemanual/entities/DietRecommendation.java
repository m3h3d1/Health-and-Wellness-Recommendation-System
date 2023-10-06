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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID dietRecommendationId;
    private String recommendationMessage;
    private LocalDateTime recommendationTime;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Insights> insights;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Breakfast> breakfast;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Lunch> lunch;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Snacks> dinner;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Dinner> snacks;

    private UUID autoDietRecommendationId;
    private String healthNote;
    private UUID recommenderId;
}
