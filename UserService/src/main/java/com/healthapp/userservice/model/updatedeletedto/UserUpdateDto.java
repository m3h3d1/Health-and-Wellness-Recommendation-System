package com.healthapp.userservice.model.updatedeletedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
}
