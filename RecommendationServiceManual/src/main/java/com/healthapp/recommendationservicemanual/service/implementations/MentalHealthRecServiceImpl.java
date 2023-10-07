package com.healthapp.recommendationservicemanual.service.implementations;

import com.healthapp.recommendationservicemanual.entities.MentalHealthRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.DuplicateEntityException;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.exceptions.InternalServerErrorException;
import com.healthapp.recommendationservicemanual.networks.RecommendationAutoMentalProxy;
import com.healthapp.recommendationservicemanual.repositories.MentalHealthRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.MentalHealthRecService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * MentalHealthRecServiceImpl is the implementation of the MentalHealthRecService interface for managing MentalHealthRecommendation entities.
 */
@Service
public class MentalHealthRecServiceImpl implements MentalHealthRecService {
    private final MentalHealthRecRepository mentalHealthRecRepository;
    private final RecommendationAutoMentalProxy recommendationAutoMentalProxy;

    /**
     * Constructs a MentalHealthRecServiceImpl instance with the required dependencies.
     *
     * @param mentalHealthRecRepository     The repository for managing MentalHealthRecommendation entities.
     * @param recommendationAutoMentalProxy The proxy for making remote calls to the mental health recommendation service.
     */
    public MentalHealthRecServiceImpl(MentalHealthRecRepository mentalHealthRecRepository, RecommendationAutoMentalProxy recommendationAutoMentalProxy) {
        this.mentalHealthRecRepository = mentalHealthRecRepository;
        this.recommendationAutoMentalProxy = recommendationAutoMentalProxy;
    }

    /**
     * Creates a new MentalHealthRecommendation entity.
     *
     * @param mentalHealthRecommendation The MentalHealthRecommendation to create.
     * @throws InternalServerErrorException If there is an issue communicating with the auto recommendation service.
     * @throws EntityNotFoundException     If no automated recommendation record exists with the given ID.
     * @throws DuplicateEntityException     If a recommendation with the same ID already exists.
     */
    @Override
    public void create(MentalHealthRecommendation mentalHealthRecommendation) {
        ResponseEntity<Boolean> response = recommendationAutoMentalProxy.ifExistsMentalHealthRec(mentalHealthRecommendation.getMentalHealthRecId());
        if(response.getStatusCode() != HttpStatus.OK) {
            throw new InternalServerErrorException("Failed to communicate with auto recommendation service");
        }
        if(!response.getBody()) {
            throw new EntityNotFoundException("There is no automated recommendation record exists with given ID"
                    + mentalHealthRecommendation.getMentalHealthRecId());
        }
        if(mentalHealthRecRepository.existsById(mentalHealthRecommendation.getMentalHealthRecId())){
            throw new DuplicateEntityException("Can't create recommendation as the recommendation already exists, try to update!");
        }
        mentalHealthRecRepository.save(mentalHealthRecommendation);
    }

    /**
     * Reads a MentalHealthRecommendation entity by its ID.
     *
     * @param recId The ID of the MentalHealthRecommendation to read.
     * @return The MentalHealthRecommendation with the specified ID.
     * @throws EntityNotFoundException If no MentalHealthRecommendation is found with the given ID.
     */
    @Override
    public MentalHealthRecommendation readById(UUID recId) {
        return mentalHealthRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Mental health recommendation not found with ID: " + recId));
    }

    /**
     * Updates a MentalHealthRecommendation entity.
     *
     * @param mentalHealthRecommendation The MentalHealthRecommendation to update.
     * @throws EntityNotFoundException If no MentalHealthRecommendation is found with the ID of the provided MentalHealthRecommendation.
     */
    @Override
    public void update(MentalHealthRecommendation mentalHealthRecommendation) {
        readById(mentalHealthRecommendation.getMentalHealthRecId());
        mentalHealthRecRepository.save(mentalHealthRecommendation);
    }

    /**
     * Deletes a MentalHealthRecommendation entity.
     *
     * @param mentalHealthRecommendation The MentalHealthRecommendation to delete.
     * @throws EntityNotFoundException If no MentalHealthRecommendation is found with the ID of the provided MentalHealthRecommendation.
     */
    @Override
    public void delete(MentalHealthRecommendation mentalHealthRecommendation) {
        readById(mentalHealthRecommendation.getMentalHealthRecId());
        mentalHealthRecRepository.delete(mentalHealthRecommendation);
    }
}
