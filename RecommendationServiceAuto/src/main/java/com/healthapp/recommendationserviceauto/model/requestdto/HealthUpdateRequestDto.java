package com.healthapp.recommendationserviceauto.model.requestdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@RequiredArgsConstructor
public class HealthUpdateRequestDto {
    private String gender;
    private Date dateOfBirth;
    private String bloodGroup;
    private Boolean vegetarian;
    private Integer goalWeight;
    private Integer targetPeriod;
}
