package com.healthapp.userservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Contact")
public class Contact {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "contact-id")
    private UUID contactId;
    @Column(name = "user-id",nullable = false)
    private UUID userId;
    @Column(name = "phone-primary", nullable = false, unique = true)
    private String primaryPhoneNumber;
    @Column(name = "phone-optional", nullable = false, unique = true)
    private String optionalPhoneNumber;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "area")
    private String area;
    @Column(name = "road-number")
    private Integer roadNumber;
    @Column(name = "block-number")
    private Character blockNumber;
    @Column(name = "house-number")
    private Integer houseNumber;
}
