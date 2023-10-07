package com.healthapp.recommendationserviceauto.model.entrydatadto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExerciseRequestDto {
    private String description;
    private String category;
    private String intensity;
    private Double duration;
    private Double calorieBurn;
}
