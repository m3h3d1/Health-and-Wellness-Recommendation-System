package com.healthapp.recommendationserviceauto.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SmokerAllergyRequestDto {
    private Boolean allergies;
    private Boolean isSmoker;
}
