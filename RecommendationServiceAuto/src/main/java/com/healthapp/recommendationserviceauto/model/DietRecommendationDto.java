package com.healthapp.recommendationserviceauto.model;

import com.healthapp.recommendationserviceauto.domain.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class DietRecommendationDto {
    private String message;
    private LocalDateTime date;
    private List<Meal> breakfast;
    private List<Meal> lunch;
    private List<Meal> dinner;
    private List<Meal> snacks;
}
