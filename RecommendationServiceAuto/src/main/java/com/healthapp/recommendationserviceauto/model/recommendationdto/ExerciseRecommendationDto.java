package com.healthapp.recommendationserviceauto.model.recommendationdto;

import com.healthapp.recommendationserviceauto.domain.Disease;
import com.healthapp.recommendationserviceauto.domain.Exercise;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ExerciseRecommendationDto {
    private String insights;
    private String message;
    private LocalDateTime date;
    private List<Exercise> exerciseList;
}
