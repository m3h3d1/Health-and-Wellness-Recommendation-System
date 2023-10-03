package com.healthapp.recommendationserviceauto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class HeartRate {
    @Id
    private UUID id;
    private LocalDateTime dateTime;
    private String beatsPerMin;

    @ManyToOne
    @JsonIgnore
    private Health health;
}