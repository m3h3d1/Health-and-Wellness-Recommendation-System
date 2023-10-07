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

@Service
public class MentalHealthRecServiceImpl implements MentalHealthRecService {
    private final MentalHealthRecRepository mentalHealthRecRepository;
    private final RecommendationAutoMentalProxy recommendationAutoMentalProxy;

    public MentalHealthRecServiceImpl(MentalHealthRecRepository mentalHealthRecRepository, RecommendationAutoMentalProxy recommendationAutoMentalProxy) {
        this.mentalHealthRecRepository = mentalHealthRecRepository;
        this.recommendationAutoMentalProxy = recommendationAutoMentalProxy;
    }

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

    @Override
    public MentalHealthRecommendation readById(UUID recId) {
        return mentalHealthRecRepository.findById(recId)
                .orElseThrow(() -> new EntityNotFoundException("Mental health recommendation not found with ID: " + recId));
    }

    @Override
    public void update(MentalHealthRecommendation mentalHealthRecommendation) {
        readById(mentalHealthRecommendation.getMentalHealthRecId());
        mentalHealthRecRepository.save(mentalHealthRecommendation);
    }

    @Override
    public void delete(MentalHealthRecommendation mentalHealthRecommendation) {
        readById(mentalHealthRecommendation.getMentalHealthRecId());
        mentalHealthRecRepository.delete(mentalHealthRecommendation);
    }
}
