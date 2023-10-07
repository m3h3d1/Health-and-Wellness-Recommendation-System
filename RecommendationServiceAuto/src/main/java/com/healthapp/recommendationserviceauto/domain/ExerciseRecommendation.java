package com.healthapp.recommendationserviceauto.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
public class ExerciseRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID exerciseRecommendationId;
    private UUID userId;
    private LocalDateTime dateTime;
    private Double sugarLevel;
    private Double lowBloodPressure;
    private Double highBloodPressure;
    private Integer age;
    private Double weight;
    private Integer goalWeight;
    private Integer targetPeriod;
    private String message;
    private String insights;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Disease> diseaseList;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Exercise> exerciseList;

    public void copyDiseaseData(List<Disease> diseases){
        diseaseList = new ArrayList<>();
        diseaseList.addAll(diseases);
    }
}
