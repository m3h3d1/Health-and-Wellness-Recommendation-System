package com.healthapp.recommendationserviceauto.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Health {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID healthId;

    private Integer age;
    private String gender;
    private Boolean allergies;
    private Boolean smokingStatus;

    @OneToMany(mappedBy = "health")
    private List<HealthNotes> healthNotes = new ArrayList<>();

    @OneToOne
    private ActivityFactor dailyActivity;

    @OneToOne
    private SleepSchedule sleepSchedule;

    @OneToMany(mappedBy = "health", cascade = CascadeType.ALL)
    private List<Weight> weights;

    @OneToMany(mappedBy = "health", cascade = CascadeType.ALL)
    private List<Height> heights;

    @OneToMany(mappedBy = "health", cascade = CascadeType.ALL)
    private List<Diabetes> diabetes;

    @OneToMany(mappedBy = "health", cascade = CascadeType.ALL)
    private List<BloodPressure> bloodPressures;

    @OneToMany(mappedBy = "health", cascade = CascadeType.ALL)
    private List<HeartRate> heartRates;

    private UUID userId;
}
