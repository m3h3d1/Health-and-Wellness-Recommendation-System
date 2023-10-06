package com.healthapp.recommendationserviceauto.model;

import com.healthapp.recommendationserviceauto.domain.Exercise;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SleepRecommendationDto {
    private String insights;
    private String message;
    private LocalDateTime date;
    private Integer duration;
    private LocalTime bedTime;
    private LocalTime wakeTime;
}
