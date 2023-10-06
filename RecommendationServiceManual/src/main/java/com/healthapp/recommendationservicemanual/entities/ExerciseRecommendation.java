package com.healthapp.recommendationservicemanual.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class ExerciseRecommendation {
    @Id
    private UUID exerciseRecommendationId;
    private UUID userId;
    private LocalDateTime recommendationTime;
    private String message;
    private String insights;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Exercise> exerciseList;

    private String healthNote;
    private UUID recommenderId;
}
