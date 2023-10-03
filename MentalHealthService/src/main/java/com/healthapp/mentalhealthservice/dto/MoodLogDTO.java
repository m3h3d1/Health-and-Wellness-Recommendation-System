package com.healthapp.mentalhealthservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
//@NoArgsConstructor
public class MoodLogDTO {
    private LocalDateTime date;
    private BigDecimal moodRating;
    private String note;
    private UUID userId;
}
