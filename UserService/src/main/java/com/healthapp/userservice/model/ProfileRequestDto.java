package com.healthapp.userservice.model;

import com.healthapp.userservice.domain.Profile;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequestDto {
    private UUID userId;
    private Profile.Gender gender;
    private Date dateOfBirth;
    private String bloodGroup;
    private Boolean vegetarian;
    private Integer goalWeight;
    private Integer targetPeriod;
}
