package com.healthapp.feedbackanalysis.dto;

import com.healthapp.feedbackanalysis.enums.RecommendationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private UUID feedbackId;
    private UUID userId;
    private UUID recommendationId;
    private RecommendationType recommendationType;
    private int rating;
    private String comment;
    private LocalDateTime feedbackDate;
}