package com.healthapp.userservice.model.Requestdto;

import lombok.*;


@Getter
@Setter
@RequiredArgsConstructor
public class ChangePasswordDto {
    private String oldPassword;
    private String newPassword;
}
