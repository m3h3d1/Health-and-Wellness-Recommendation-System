package com.healthapp.userservice.model.Requestdto;

import com.healthapp.userservice.domain.Contact;
import com.healthapp.userservice.domain.Profile;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private Contact contact;
    private Profile profile;
}
