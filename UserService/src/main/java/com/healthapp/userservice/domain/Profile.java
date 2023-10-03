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
@Table(name = "Profile")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "profile-id")
    private UUID profileId;
    @Column(name = "user-id")
    private UUID userId;
    @Column(name = "gender")
    private String gender;
    @Column(name = "date-of-birth")
    private Date dateOfBirth;
    @Column(name = "blood-group")
    private String bloodGroup;
    @Column(name ="vegetarian")
    private Boolean vegetarian;
    @Column(name = "goal-weight")
    private Integer goalWeight;
    @Column(name = "target-period")
    private Integer targetPeriod;
}
