package com.healthapp.userservice.model;

import com.healthapp.userservice.domain.Contact;
import com.healthapp.userservice.domain.Profile;
import com.healthapp.userservice.domain.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
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
