package com.healthapp.communityservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Getter @Setter
public class Following {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID followedUserId;
    private LocalDateTime followTime;
}
