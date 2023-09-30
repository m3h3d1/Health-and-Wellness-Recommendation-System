package com.healthapp.userservice.model;

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
    private String gender;
    private Date dateOfBirth;
    private String bloodGroup;
    private boolean vegetarian;
    private int goalWeight;
    private int targetPeriod;
}
