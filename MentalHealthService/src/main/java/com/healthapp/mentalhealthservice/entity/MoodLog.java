package com.healthapp.mentalhealthservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class MoodLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID moodLogId;
    private LocalDateTime date;
    private BigDecimal moodRating;
    private String note;
    private UUID userId;
}
