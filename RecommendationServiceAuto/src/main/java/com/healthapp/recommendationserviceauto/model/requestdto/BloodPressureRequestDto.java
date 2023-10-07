package com.healthapp.recommendationserviceauto.model.requestdto;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BloodPressureRequestDto {
    private Double highPressure;
    private Double lowPressure;
}
