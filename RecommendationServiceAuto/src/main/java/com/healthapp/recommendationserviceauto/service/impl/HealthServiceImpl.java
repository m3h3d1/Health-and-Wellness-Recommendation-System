package com.healthapp.recommendationserviceauto.service.impl;

import com.healthapp.recommendationserviceauto.domain.BloodPressure;
import com.healthapp.recommendationserviceauto.domain.Health;
import com.healthapp.recommendationserviceauto.model.BloodPressureRequestDto;
import com.healthapp.recommendationserviceauto.model.HealthRequestDto;
import com.healthapp.recommendationserviceauto.model.ProfileResponseDto;
import com.healthapp.recommendationserviceauto.networkmanager.UserFeignClient;
import com.healthapp.recommendationserviceauto.repository.BloodPressureRepository;
import com.healthapp.recommendationserviceauto.repository.HealthRepository;
import com.healthapp.recommendationserviceauto.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class HealthServiceImpl implements HealthService {
    @Autowired
    private HealthRepository healthRepository;
    @Autowired
    private BloodPressureRepository bloodPressureRepository;
    @Autowired
    private UserFeignClient userFeignClient;
    private int calculateAge(Date dateOfBirth) {
        Date currentDate = new Date();
        long timeDiff = currentDate.getTime() - dateOfBirth.getTime();
        long ageInMillis = timeDiff;
        int ageInYears = (int) (ageInMillis / (1000L * 60 * 60 * 24 * 365));
        return ageInYears;
    }
    @Override
    public void addHealthData(UUID userId, HealthRequestDto healthRequestDto) {
        ResponseEntity<ProfileResponseDto> response= userFeignClient.getProfileInfo(userId);
        ProfileResponseDto profileResponseDto= response.getBody();
        Health health = new Health();
        health.setUserId(userId);
        health.setAllergies(healthRequestDto.getAllergies());
        health.setSmokingStatus(healthRequestDto.getSmokingStatus());
        health.setGender(profileResponseDto.getGender());
        health.setAge(calculateAge(profileResponseDto.getDateOfBirth()));
        healthRepository.save(health);
    }

    @Override
    public void addBloodPressureData(UUID userId, BloodPressureRequestDto bloodPressureRequestDto) {
        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setPressure(bloodPressureRequestDto.getPressure());
        bloodPressure.setDateTime(LocalDateTime.now());
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);
        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            bloodPressure.setHealth(health);
            health.getBloodPressures().add(bloodPressure);
            bloodPressureRepository.save(bloodPressure);
            healthRepository.save(health);
        }
    }

}
