package com.healthapp.userservice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class HealthRequestDto {
    private UUID userId;
    private String gender;
    private Date dateOfBirth;
    private Integer goalWeight;
    private Integer targetPeriod;
}
