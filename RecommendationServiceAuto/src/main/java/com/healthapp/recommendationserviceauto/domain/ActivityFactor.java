package com.healthapp.recommendationserviceauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class ActivityFactor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String activityLevel;
    private Double factor;
}