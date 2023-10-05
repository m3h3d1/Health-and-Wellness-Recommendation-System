package com.healthapp.recommendationserviceauto.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BloodPressureRequestDto {
    private Double pressure;
}
