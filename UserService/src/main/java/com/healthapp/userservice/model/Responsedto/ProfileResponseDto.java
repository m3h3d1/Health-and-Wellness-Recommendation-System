package com.healthapp.userservice.model.Responsedto;

import com.healthapp.userservice.domain.BloodGroupEnum;
import com.healthapp.userservice.domain.GenderEnum;
import com.healthapp.userservice.domain.Profile;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class ProfileResponseDto {
    private GenderEnum gender;
    private Date dateOfBirth;
    private BloodGroupEnum bloodGroup;
    private Boolean vegetarian;
    private Integer goalWeight;
    private Integer targetPeriod;
}
