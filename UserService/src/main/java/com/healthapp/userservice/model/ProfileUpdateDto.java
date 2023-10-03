package com.healthapp.userservice.model;

import com.healthapp.userservice.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileUpdateDto {
    private Profile.Gender gender;
    private Date dateOfBirth;
    private String bloodGroup;
    private Boolean vegetarian;
    private Integer goalWeight;
    private Integer targetPeriod;
}
