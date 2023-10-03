package com.healthapp.userservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @Column(name = "user-id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID user_Id;
    @Column(name = "first-name")
    private String firstName;
    @Column(name = "last-name")
    private String lastName;
    @Column(name = "user-name")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    public enum Roles {
        User,
        Admin,
        Doctor,
        GymTrainer,
        YogaTrainer,
        Nutritionist
    }
    @Enumerated(EnumType.STRING)
    private Roles roles;
    @OneToOne
    private Contact contact;
    @OneToOne
    private Profile profile;
}
