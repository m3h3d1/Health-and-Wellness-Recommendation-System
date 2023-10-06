package com.healthapp.recommendationservicemanual.service.impl;

import com.healthapp.recommendationservicemanual.entities.MentalHealthRecommendation;
import com.healthapp.recommendationservicemanual.repositories.MentalHealthRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.MentalHealthRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MentalHealthRecServiceImpl implements MentalHealthRecService {
    private final MentalHealthRecRepository mentalHealthRecRepository;

    @Autowired
    public MentalHealthRecServiceImpl(MentalHealthRecRepository mentalHealthRecRepository) {
        this.mentalHealthRecRepository = mentalHealthRecRepository;
    }

    @Override
    public void create(MentalHealthRecommendation mentalHealthRecommendation) {
        mentalHealthRecRepository.save(mentalHealthRecommendation);
    }

    @Override
    public MentalHealthRecommendation readById(UUID recId) {
        return mentalHealthRecRepository.findById(recId).orElse(null);
    }

    @Override
    public void update(MentalHealthRecommendation mentalHealthRecommendation) {
        mentalHealthRecRepository.save(mentalHealthRecommendation);
    }

    @Override
    public void delete(MentalHealthRecommendation mentalHealthRecommendation) {
        mentalHealthRecRepository.delete(mentalHealthRecommendation);
    }
}
