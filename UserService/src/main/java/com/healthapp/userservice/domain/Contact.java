package com.healthapp.userservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "Contact")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "contact-id")
    private UUID contactId;
    @Column(name = "user-id")
    private UUID userId;
    @Column(name = "phone-primary")
    private String primaryPhoneNumber;
    @Column(name = "phone-optional")
    private String optionalPhoneNumber;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
}
