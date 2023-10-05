package com.healthapp.notificationservice.entities;

import com.healthapp.notificationservice.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @RequiredArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    // References
    private UUID notificationId;
    private UUID userId;
    // Notification properties
    private String text;
    private LocalDateTime timeCreate;
    private boolean seen;
    private String type;
}
