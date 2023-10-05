package com.healthapp.feedbackprogress.dto.healthdto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class HealthNotes {
    private UUID id;
    private LocalDateTime dateTime;
    private String content;

    @JsonIgnore
    private HealthDTO health;
}
