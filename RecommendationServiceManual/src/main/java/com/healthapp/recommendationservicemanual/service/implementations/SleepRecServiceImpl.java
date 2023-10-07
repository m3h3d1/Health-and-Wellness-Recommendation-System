package com.healthapp.recommendationservicemanual.service.implementations;

import com.healthapp.recommendationservicemanual.entities.SleepRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.exceptions.InternalServerErrorException;
import com.healthapp.recommendationservicemanual.networks.RecommendationAutoProxy;
import com.healthapp.recommendationservicemanual.repositories.SleepRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.SleepRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SleepRecServiceImpl implements SleepRecService {
    private final SleepRecRepository sleepRecRepository;
    private final RecommendationAutoProxy recommendationAutoProxy;

    @Autowired
    public SleepRecServiceImpl(SleepRecRepository sleepRecRepository, RecommendationAutoProxy recommendationAutoProxy) {
        this.sleepRecRepository = sleepRecRepository;
        this.recommendationAutoProxy = recommendationAutoProxy;
    }

    public void create(SleepRecommendation sleepRecommendation) {
        try{
            ResponseEntity<Boolean> response = recommendationAutoProxy.ifExistsSleepRec(sleepRecommendation.getSleepRecommendationId());
            if (response.getStatusCode() != HttpStatus.OK){
                throw new EntityNotFoundException
                        ("There is no automated recommendation record exists with given ID"
                                + sleepRecommendation.getSleepRecommendationId());
            }
        }
        catch (Exception internalCommunicationException){
            throw new InternalServerErrorException("Failed to communicate with auto recommendation service");
        }
        sleepRecRepository.save(sleepRecommendation);
    }

    @Override
    public SleepRecommendation readById(UUID recId) {
        return sleepRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Sleep recommendation not found with ID: " + recId));
    }

    @Override
    public void update(SleepRecommendation sleepRecommendation) {
        readById(sleepRecommendation.getSleepRecommendationId());
        sleepRecRepository.save(sleepRecommendation);
    }

    @Override
    public void delete(SleepRecommendation sleepRecommendation) {
        readById(sleepRecommendation.getSleepRecommendationId());
        sleepRecRepository.delete(sleepRecommendation);
    }
}
