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

@Service
public class DietRecServiceImpl implements DietRecService {
    private final DietRecRepository dietRecRepository;
    private final RecommendationAutoProxy recommendationAutoProxy;

    public DietRecServiceImpl(DietRecRepository dietRecRepository, RecommendationAutoProxy recommendationAutoProxy) {
        this.dietRecRepository = dietRecRepository;
        this.recommendationAutoProxy = recommendationAutoProxy;
    }

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

    @Override
    public DietRecommendation readById(UUID recId) {
        return dietRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Diet recommendation not found with ID: " + recId));
    }

    @Override
    public void update(DietRecommendation dietRecommendation) {
        readById(dietRecommendation.getDietRecommendationId());
        dietRecRepository.save(dietRecommendation);
    }

    @Override
    public void delete(DietRecommendation dietRecommendation) {
        readById(dietRecommendation.getDietRecommendationId());
        dietRecRepository.delete(dietRecommendation);
    }
}
