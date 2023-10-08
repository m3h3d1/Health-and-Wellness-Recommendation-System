package com.healthapp.userservice.model.updatedeletedto;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@RequiredArgsConstructor
public class UserDeleteDto {
    private UUID userId;
}
