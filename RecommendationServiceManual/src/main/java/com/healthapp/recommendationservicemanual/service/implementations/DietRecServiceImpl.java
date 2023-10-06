package com.healthapp.recommendationservicemanual.service.impl;

import com.healthapp.recommendationservicemanual.entities.DietRecommendation;
import com.healthapp.recommendationservicemanual.repositories.DietRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.DietRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DietRecServiceImpl implements DietRecService {
    private final DietRecRepository dietRecRepository;

    @Autowired
    public DietRecServiceImpl(DietRecRepository dietRecRepository) {
        this.dietRecRepository = dietRecRepository;
    }

    @Override
    public void create(DietRecommendation dietRecommendation) {
        dietRecRepository.save(dietRecommendation);
    }

    @Override
    public DietRecommendation readById(UUID recId) {
        return dietRecRepository.findById(recId).orElse(null);
    }

    @Override
    public void update(DietRecommendation dietRecommendation) {
        dietRecRepository.save(dietRecommendation);
    }

    @Override
    public void delete(DietRecommendation dietRecommendation) {
        dietRecRepository.delete(dietRecommendation);
    }
}
