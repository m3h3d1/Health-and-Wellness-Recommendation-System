package com.healthapp.userservice.model.updatedeletedto;

import com.healthapp.userservice.domain.Contact;
import com.healthapp.userservice.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private Contact contact;
    private Profile profile;
}
