package com.healthapp.userservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "user-id", nullable = false)
    private UUID userId;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Column(name = "date-of-birth", nullable = false)
    private Date dateOfBirth;
    @Enumerated(EnumType.STRING)
    @Column(name = "blood-group", nullable = false)
    private BloodGroup bloodGroup;
    @Column(name ="vegetarian", nullable = false)
    private Boolean vegetarian;
    @Column(name = "goal-weight", nullable = false)
    private Integer goalWeight;
    @Column(name = "target-period", nullable = false)
    private Integer targetPeriod;
    public enum Gender {
        Male,
        Female
    }
    public enum BloodGroup {
        A_POSITIVE("A+"),
        A_NEGATIVE("A-"),
        B_POSITIVE("B+"),
        B_NEGATIVE("B-"),
        AB_POSITIVE("AB+"),
        AB_NEGATIVE("AB-"),
        O_POSITIVE("O+"),
        O_NEGATIVE("O-");

        private final String value;

        BloodGroup(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        public static BloodGroup fromString(String text) {
            for (BloodGroup bg : BloodGroup.values()) {
                if (bg.value.equalsIgnoreCase(text)) {
                    return bg;
                }
            }
            throw new IllegalArgumentException("No constant with text " + text + " found");
        }
    }


}
