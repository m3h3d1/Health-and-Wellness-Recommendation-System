package com.healthapp.feedbackprogress.dto.healthdto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class HealthDTO {
    private UUID healthId;
    private Integer age;
    private String gender;
    private Boolean allergies;
    private Boolean smokingStatus;
    private Integer goalWeight;
    private Integer targetPeriod;

    private List<HealthNotes> healthNotes = new ArrayList<>();

    private ActivityFactor dailyActivity;

    private SleepSchedule sleepSchedule;

    private List<Weight> weights;

    private List<Height> heights;

    private List<Diabetes> diabetes;

    private List<BloodPressure> bloodPressures;

    private List<HeartRate> heartRates;

    private List<Disease> diseases;

    private UUID userId;
}
