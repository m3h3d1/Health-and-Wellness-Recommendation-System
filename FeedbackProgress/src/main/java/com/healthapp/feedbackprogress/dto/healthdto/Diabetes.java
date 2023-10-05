package com.healthapp.feedbackprogress.dto.healthdto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class Diabetes {
    private UUID id;
    private LocalDateTime dateTime;
    private Double sugarLevel;

    @JsonIgnore
    private HealthDTO health;
}