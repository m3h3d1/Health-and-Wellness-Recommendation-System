package com.healthapp.userservice.model.Responsedto;

import com.healthapp.userservice.domain.Contact;
import com.healthapp.userservice.domain.Profile;
import com.healthapp.userservice.domain.Role;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private List<Role> roles;
    private Contact contact;
    private Profile profile;
}
