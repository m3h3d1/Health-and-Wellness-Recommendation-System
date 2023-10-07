package com.healthapp.userservice.model.Responsedto;

import com.healthapp.userservice.domain.BloodGroupEnum;
import com.healthapp.userservice.domain.GenderEnum;
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
public class ProfileResponseDto {
    private GenderEnum gender;
    private Date dateOfBirth;
    private BloodGroupEnum bloodGroup;
    private Boolean vegetarian;
    private Integer goalWeight;
    private Integer targetPeriod;
}
