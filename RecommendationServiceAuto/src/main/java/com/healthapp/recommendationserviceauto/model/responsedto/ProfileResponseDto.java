package com.healthapp.recommendationserviceauto.model.responsedto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class ProfileResponseDto {
    private String gender;
    private Date dateOfBirth;
    private String bloodGroup;
    private Boolean vegetarian;
    private Integer goalWeight;
    private Integer targetPeriod;
}