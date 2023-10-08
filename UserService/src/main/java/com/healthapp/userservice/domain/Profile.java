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
@Table(name = "Profile")
public class Profile {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "profile-id")
    private UUID profileId;
    @Column(name = "user-id", nullable = false)
    private UUID userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private GenderEnum gender;
    @Column(name = "date-of-birth", nullable = false)
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(name = "blood-group", nullable = false)
    private BloodGroupEnum bloodGroup;
    @Column(name ="vegetarian", nullable = false)
    private Boolean vegetarian;
    @Column(name = "goal-weight", nullable = false)
    private Integer goalWeight;
    @Column(name = "target-period", nullable = false)
    private Integer targetPeriod;
}
