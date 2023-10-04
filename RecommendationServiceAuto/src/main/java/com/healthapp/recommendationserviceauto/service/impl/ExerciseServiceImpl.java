package com.healthapp.recommendationserviceauto.service.impl;

import com.healthapp.recommendationserviceauto.domain.BloodPressure;
import com.healthapp.recommendationserviceauto.domain.Health;
import com.healthapp.recommendationserviceauto.model.ExerciseRecommendationDto;
import com.healthapp.recommendationserviceauto.model.ProfileResponseDto;
import com.healthapp.recommendationserviceauto.networkmanager.UserFeignClient;
import com.healthapp.recommendationserviceauto.repository.BloodPressureRepository;
import com.healthapp.recommendationserviceauto.repository.HealthRepository;
import com.healthapp.recommendationserviceauto.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Autowired
    private HealthRepository healthRepository;
    @Autowired
    private BloodPressureRepository bloodPressureRepository;
    @Autowired
    private UserFeignClient userFeignClient;
    @Override
    public ExerciseRecommendationDto recommend(UUID userId) {
        ResponseEntity<ProfileResponseDto> response= userFeignClient.getProfileInfo(userId);
        ProfileResponseDto profileResponseDto= response.getBody();
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);
        List<BloodPressure> bloodPressures = bloodPressureRepository.findByHealth(healthOptional.get());
        Integer age = healthOptional.get().getAge();
        Double pressure=bloodPressures.get(0).getPressure();
        Integer goalWeight = profileResponseDto.getGoalWeight();
        Integer targetPeriod = profileResponseDto.getTargetPeriod();
        //incomplete
        return null;
    }
}
