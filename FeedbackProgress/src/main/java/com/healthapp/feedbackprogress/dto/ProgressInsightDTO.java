package com.healthapp.feedbackprogress.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProgressInsightDTO {
    private UUID userId;
    private Integer age;
    private String gender;
    private Integer goalWeight;
    private Integer targetPeriod;

    private String heightInsight;
    private String weightInsight;
    private String diabetesInsight;
    private String bloodPressureInsight;
    private String heartRateInsight;
    private String diseaseInsight;
}
