package com.healthapp.userservice.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactRequestDto {
    private UUID userId;
    private String primaryPhoneNumber;
    private String optionalPhoneNumber;
    private String country;
    private String city;
    private String area;
    private Integer roadNumber;
    private Character blockNumber;
    private Integer houseNumber;
}
