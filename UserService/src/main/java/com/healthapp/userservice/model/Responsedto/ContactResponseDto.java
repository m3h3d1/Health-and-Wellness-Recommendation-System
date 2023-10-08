package com.healthapp.userservice.model.Responsedto;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class ContactResponseDto {
    private String primaryPhoneNumber;
    private String optionalPhoneNumber;
    private String country;
    private String city;
    private String area;
    private Integer roadNumber;
    private Character blockNumber;
    private Integer houseNumber;
}
