package com.healthapp.mentalhealthservice.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
//@NoArgsConstructor
public class MentalHealthExerciseDTO {
    private String category;
    private String description;
    private int duration;
    private String suggestedForIssues;
    private String benefits;
    private String notes;
    private UUID userId;
}
