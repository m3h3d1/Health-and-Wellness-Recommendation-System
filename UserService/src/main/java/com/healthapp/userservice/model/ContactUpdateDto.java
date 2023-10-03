package com.healthapp.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactUpdateDto {
    private String primaryPhoneNumber;
    private String optionalPhoneNumber;
    private String country;
    private String city;
}
