package com.healthapp.recommendationserviceauto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExtraRequestDto {
    private Boolean allergies;
    private Boolean isSmoker;
    private String activityLevel;
}
