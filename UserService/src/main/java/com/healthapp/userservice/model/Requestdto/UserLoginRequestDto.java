package com.healthapp.userservice.model.Requestdto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class UserLoginRequestDto {
    private String email;
    private String password;
}
