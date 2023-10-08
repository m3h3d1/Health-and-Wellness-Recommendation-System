package com.healthapp.userservice.model.Requestdto;

import com.healthapp.userservice.domain.GenderEnum;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class ProfileRequestDto {
    private UUID userId;
    private GenderEnum gender;
    private Date dateOfBirth;
    private String bloodGroup;
    private Boolean vegetarian;
    private Integer goalWeight;
    private Integer targetPeriod;
}
