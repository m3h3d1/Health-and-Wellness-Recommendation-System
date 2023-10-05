package com.healthapp.feedbackprogress.service.implementation;

import com.healthapp.feedbackprogress.dto.ProgressTrackDTO;
import com.healthapp.feedbackprogress.dto.healthdto.*;
import com.healthapp.feedbackprogress.network.HealthServiceProxy;
import com.healthapp.feedbackprogress.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProgressServiceImpl implements ProgressService {
    @Autowired
    HealthServiceProxy healthServiceProxy;

    @Override
    public HealthDTO getProgressTrackById(UUID userId) {
        HealthDTO health = healthServiceProxy.getHealth(userId).getBody();

        health.getWeights().sort(Comparator.comparing(Weight::getDateTime));
        health.getHeights().sort(Comparator.comparing(Height::getDateTime));
        health.getDiabetes().sort(Comparator.comparing(Diabetes::getDateTime));
        health.getWeights().sort(Comparator.comparing(Weight::getDateTime));
        health.getBloodPressures().sort(Comparator.comparing(BloodPressure::getDateTime));
        health.getHeartRates().sort(Comparator.comparing(HeartRate::getDateTime));
        health.getDiseases().sort(Comparator.comparing(Disease::getDate));

        return health;
    }
}
