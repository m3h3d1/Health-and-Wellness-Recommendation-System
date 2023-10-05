package com.healthapp.userservice.model;

import com.healthapp.userservice.domain.Contact;
import com.healthapp.userservice.domain.Profile;
import com.healthapp.userservice.domain.Role;
import com.healthapp.userservice.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private List<Role> roles;
    private Contact contact;
    private Profile profile;
}
