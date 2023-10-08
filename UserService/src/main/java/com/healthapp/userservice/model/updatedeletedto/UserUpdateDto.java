package com.healthapp.userservice.model.updatedeletedto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class UserUpdateDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
}
