package com.healthapp.userservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @Column(name = "first-name", nullable = false)
    @NotBlank(message = "First name is required!")
    @Pattern(regexp = "^(|[^\\d]+)$", message = "First name cannot contain digits")
    private String firstName;
    @Column(name = "last-name", nullable = false)
    @NotBlank(message = "Last name is required!")
    @Pattern(regexp = "^(|[^\\d]+)$", message = "Last name cannot contain digits")
    private String lastName;
    @Column(name = "user-name", nullable = false)
    @NotBlank(message = "Username is required!")
    @Pattern(regexp = "^(|[a-z0-9]+)$", message = "Username can only contain lowercase letters and numbers")
    private String userName;
    @Column(name = "password", nullable = false)
    @Size(min = 5, message = "Password must be 5 characters long")
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email must be in proper format!")
    private String email;
    @Enumerated(EnumType.STRING)
    private Roles roles;
    @OneToOne
    private Contact contact;
    @OneToOne
    private Profile profile;
    public enum Roles {
        User,
        Admin,
        Doctor,
        GymTrainer,
        YogaTrainer,
        Nutritionist
    }
}
