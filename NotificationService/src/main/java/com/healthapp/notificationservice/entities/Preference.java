package com.healthapp.notificationservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter @Setter @RequiredArgsConstructor
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID preferenceId;
    private UUID userId;
    // Personalized time stamp
    private boolean doNotDisturb;
    private LocalTime muteFrom;
    private LocalTime muteTo;
    // Personalized notification setting properties based on notification type
    private boolean getPostInteractionNotification;
    private boolean getConnectionInteractionNotification;
    private boolean getHealthFeedNotification;
    private boolean getRecommendationNotification;
    private boolean getAccountNotification;
}
