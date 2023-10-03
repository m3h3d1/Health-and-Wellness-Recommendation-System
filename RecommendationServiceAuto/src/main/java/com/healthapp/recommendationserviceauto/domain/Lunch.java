package com.healthapp.recommendationserviceauto.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Lunch {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private Double calories;
}
