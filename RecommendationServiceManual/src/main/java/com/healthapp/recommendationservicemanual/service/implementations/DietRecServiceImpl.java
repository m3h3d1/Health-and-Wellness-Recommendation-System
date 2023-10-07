package com.healthapp.recommendationservicemanual.service.implementations;

import com.healthapp.recommendationservicemanual.entities.DietRecommendation;
import com.healthapp.recommendationservicemanual.exceptions.DuplicateEntityException;
import com.healthapp.recommendationservicemanual.exceptions.EntityNotFoundException;
import com.healthapp.recommendationservicemanual.exceptions.InternalServerErrorException;
import com.healthapp.recommendationservicemanual.networks.InternalCommunicationException;
import com.healthapp.recommendationservicemanual.networks.RecommendationAutoProxy;
import com.healthapp.recommendationservicemanual.repositories.DietRecRepository;
import com.healthapp.recommendationservicemanual.service.interfaces.DietRecService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * DietRecServiceImpl is the implementation of the DietRecService interface for managing DietRecommendation entities.
 */
@Service
public class DietRecServiceImpl implements DietRecService {
    private final DietRecRepository dietRecRepository;
    private final RecommendationAutoProxy recommendationAutoProxy;

    /**
     * Constructs a DietRecServiceImpl instance with the required dependencies.
     *
     * @param dietRecRepository     The repository for managing DietRecommendation entities.
     * @param recommendationAutoProxy The proxy for making remote calls to the recommendation service.
     */
    public DietRecServiceImpl(DietRecRepository dietRecRepository, RecommendationAutoProxy recommendationAutoProxy) {
        this.dietRecRepository = dietRecRepository;
        this.recommendationAutoProxy = recommendationAutoProxy;
    }

    /**
     * Creates a new DietRecommendation entity.
     *
     * @param dietRecommendation The DietRecommendation to create.
     * @throws InternalServerErrorException If there is an issue communicating with the auto recommendation service.
     * @throws EntityNotFoundException     If no automated recommendation record exists with the given ID.
     * @throws DuplicateEntityException     If a recommendation with the same ID already exists.
     */
    @Override
    public void create(DietRecommendation dietRecommendation) {
        ResponseEntity<Boolean> response = recommendationAutoProxy.ifExistsDietRec(dietRecommendation.getDietRecommendationId());
        if(response.getStatusCode() != HttpStatus.OK) {
            throw new InternalServerErrorException("Failed to communicate with auto recommendation service");
        }
        if(!response.getBody()) {
            throw new EntityNotFoundException("There is no automated recommendation record exists with given ID"
                    + dietRecommendation.getDietRecommendationId());
        }
        if(dietRecRepository.existsById(dietRecommendation.getDietRecommendationId())){
            throw new DuplicateEntityException("Can't create recommendation as the recommendation already exists, try to update!");
        }
        dietRecRepository.save(dietRecommendation);
    }

    /**
     * Reads a DietRecommendation entity by its ID.
     *
     * @param recId The ID of the DietRecommendation to read.
     * @return The DietRecommendation with the specified ID.
     * @throws EntityNotFoundException If no DietRecommendation is found with the given ID.
     */
    @Override
    public DietRecommendation readById(UUID recId) {
        return dietRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Diet recommendation not found with ID: " + recId));
    }

    /**
     * Updates a DietRecommendation entity.
     *
     * @param dietRecommendation The DietRecommendation to update.
     * @throws EntityNotFoundException If no DietRecommendation is found with the ID of the provided DietRecommendation.
     */
    @Override
    public void update(DietRecommendation dietRecommendation) {
        readById(dietRecommendation.getDietRecommendationId());
        dietRecRepository.save(dietRecommendation);
    }

    /**
     * Deletes a DietRecommendation entity.
     *
     * @param dietRecommendation The DietRecommendation to delete.
     * @throws EntityNotFoundException If no DietRecommendation is found with the ID of the provided DietRecommendation.
     */
    @Override
    public void delete(DietRecommendation dietRecommendation) {
        readById(dietRecommendation.getDietRecommendationId());
        dietRecRepository.delete(dietRecommendation);
    }
}
