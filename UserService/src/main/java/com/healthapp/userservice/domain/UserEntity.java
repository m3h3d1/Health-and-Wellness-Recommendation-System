package com.healthapp.userservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
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
    private UUID userId;
    @Column(name = "first-name", nullable = false)
    @Pattern(regexp = "^(|[^\\d]+)$", message = "First name cannot contain digits")
    private String firstName;
    @Column(name = "last-name", nullable = false)
    @Pattern(regexp = "^(|[^\\d]+)$", message = "Last name cannot contain digits")
    private String lastName;
    @Column(name = "user-name", nullable = false)
    @Pattern(regexp = "^(|[a-z0-9]+)$", message = "Username can only contain lowercase letters and numbers")
    private String userName;
    @Column(name = "password", nullable = false)
    @Size(min = 5, message = "Password must be 5 characters long")
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles;
    @OneToOne
    private Contact contact;
    @OneToOne
    private Profile profile;
}
