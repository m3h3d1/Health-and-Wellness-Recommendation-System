package com.healthapp.feedbackprogress.service;

import com.healthapp.feedbackprogress.dto.ProgressTrackDTO;
import com.healthapp.feedbackprogress.dto.healthdto.HealthDTO;

import java.util.UUID;

public interface ProgressService {
    public HealthDTO getProgressTrackById(UUID userId);
}
