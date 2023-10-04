package com.healthapp.recommendationserviceauto.model;

import com.healthapp.recommendationserviceauto.domain.Exercise;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ExerciseRecommendationDto {
    private String insights;
    private String text;
    private List<Exercise> exerciseList;
}
