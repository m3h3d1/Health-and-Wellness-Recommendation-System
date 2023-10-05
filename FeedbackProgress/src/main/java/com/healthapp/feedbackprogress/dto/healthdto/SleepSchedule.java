package com.healthapp.feedbackprogress.dto.healthdto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor
public class SleepSchedule {
    private UUID id;
    private LocalTime bedTime;
    private LocalTime wakeTime;

    @JsonIgnore
    private HealthDTO health;
}
