package com.healthapp.recommendationserviceauto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class HealthRequestDto {
    private Boolean allergies;
    private Boolean smokingStatus;
}
