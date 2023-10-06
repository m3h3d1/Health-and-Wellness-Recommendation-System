package com.healthapp.recommendationserviceauto.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class SleepRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID sleepRecommendationId;
    private UUID userId;
    private LocalDateTime dateTime;
    private Integer age;
    private String gender;
    private Double weight;
    private String message;
    private String insights;
    private Integer duration;
    private LocalTime bedTime;
    private LocalTime wakeTime;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Disease> diseaseList;

    public void copyDiseaseData(List<Disease> diseases){
        diseaseList = new ArrayList<>();
        diseaseList.addAll(diseases);
    }
}
