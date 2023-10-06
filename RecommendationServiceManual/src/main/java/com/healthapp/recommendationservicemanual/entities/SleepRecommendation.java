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
@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class SleepRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sleepRecommendationId;
    private UUID userId;
    private LocalDateTime recommendationTime;

    private UUID autoDietRecommendationId;
    private String healthNote;
    private UUID recommenderId;
}
