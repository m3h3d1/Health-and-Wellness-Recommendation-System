package com.healthapp.mentalhealthservice.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class MentalHealthExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID mentalHealthExerciseId;
    private String category;
    private String description;
    private int duration;
    private String suggestedForIssues;
    private String benefits;
    private String notes;

}
