package com.healthapp.userservice.model.Requestdto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
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
