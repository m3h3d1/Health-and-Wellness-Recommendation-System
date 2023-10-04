package com.healthapp.communityservice.networks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Object roles;
    private Object contact;
    private Object profile;
}