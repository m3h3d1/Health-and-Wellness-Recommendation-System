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

/**
 * SleepRecServiceImpl is the implementation of the SleepRecService interface for managing SleepRecommendation entities.
 */
@Service
public class SleepRecServiceImpl implements SleepRecService {
    private final SleepRecRepository sleepRecRepository;
    private final RecommendationAutoProxy recommendationAutoProxy;

    /**
     * Constructs a SleepRecServiceImpl instance with the required dependencies.
     *
     * @param sleepRecRepository     The repository for managing SleepRecommendation entities.
     * @param recommendationAutoProxy The proxy for making remote calls to the sleep recommendation service.
     */
    @Autowired
    public SleepRecServiceImpl(SleepRecRepository sleepRecRepository, RecommendationAutoProxy recommendationAutoProxy) {
        this.sleepRecRepository = sleepRecRepository;
        this.recommendationAutoProxy = recommendationAutoProxy;
    }

    /**
     * Creates a new SleepRecommendation entity.
     *
     * @param sleepRecommendation The SleepRecommendation to create.
     * @throws EntityNotFoundException     If no automated recommendation record exists with the given ID.
     * @throws InternalServerErrorException If there is an issue communicating with the auto recommendation service.
     */
    public void create(SleepRecommendation sleepRecommendation) {
        try {
            ResponseEntity<Boolean> response = recommendationAutoProxy.ifExistsSleepRec(sleepRecommendation.getSleepRecommendationId());
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new EntityNotFoundException("There is no automated recommendation record exists with given ID"
                        + sleepRecommendation.getSleepRecommendationId());
            }
        } catch (Exception internalCommunicationException) {
            throw new InternalServerErrorException("Failed to communicate with auto recommendation service");
        }
        sleepRecRepository.save(sleepRecommendation);
    }

    /**
     * Reads a SleepRecommendation entity by its ID.
     *
     * @param recId The ID of the SleepRecommendation to read.
     * @return The SleepRecommendation with the specified ID.
     * @throws EntityNotFoundException If no SleepRecommendation is found with the given ID.
     */
    @Override
    public SleepRecommendation readById(UUID recId) {
        return sleepRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Sleep recommendation not found with ID: " + recId));
    }

    /**
     * Updates a SleepRecommendation entity.
     *
     * @param sleepRecommendation The SleepRecommendation to update.
     * @throws EntityNotFoundException If no SleepRecommendation is found with the ID of the provided SleepRecommendation.
     */
    @Override
    public void update(SleepRecommendation sleepRecommendation) {
        readById(sleepRecommendation.getSleepRecommendationId());
        sleepRecRepository.save(sleepRecommendation);
    }

    /**
     * Deletes a SleepRecommendation entity.
     *
     * @param sleepRecommendation The SleepRecommendation to delete.
     * @throws EntityNotFoundException If no SleepRecommendation is found with the ID of the provided SleepRecommendation.
     */
    @Override
    public void delete(SleepRecommendation sleepRecommendation) {
        readById(sleepRecommendation.getSleepRecommendationId());
        sleepRecRepository.delete(sleepRecommendation);
    }
}
