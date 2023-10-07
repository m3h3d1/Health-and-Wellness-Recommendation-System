package com.healthapp.recommendationservicemanual.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

// Entity class representing Insights
@Getter @Setter @RequiredArgsConstructor @Entity
public class Insights {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String text; // Textual content of the insight
}
