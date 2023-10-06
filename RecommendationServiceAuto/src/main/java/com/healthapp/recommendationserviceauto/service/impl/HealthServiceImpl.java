package com.healthapp.recommendationserviceauto.service.impl;

import com.healthapp.recommendationserviceauto.domain.*;
import com.healthapp.recommendationserviceauto.model.*;
import com.healthapp.recommendationserviceauto.networkmanager.UserFeignClient;
import com.healthapp.recommendationserviceauto.repository.*;
import com.healthapp.recommendationserviceauto.service.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    @Autowired
    private DiabetesRepository diabetesRepository;
    @Autowired
    private DiseaseRepository diseaseRepository;
    @Autowired
    private WeightRepository weightRepository;
    private int calculateAge(Date dateOfBirth) {
        Date currentDate = new Date();
        long timeDiff = currentDate.getTime() - dateOfBirth.getTime();
        long ageInMillis = timeDiff;
        int ageInYears = (int) (ageInMillis / (1000L * 60 * 60 * 24 * 365));
        return ageInYears;
    }
    @Override
    public void addHealthData(UUID userId, HealthRequestDto healthRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Health health = new Health();
        health.setUserId(UUID.fromString(authentication.getName()));
        health.setGender(healthRequestDto.getGender());
        health.setAge(calculateAge(healthRequestDto.getDateOfBirth()));
        health.setGoalWeight(healthRequestDto.getGoalWeight());
        health.setTargetPeriod(healthRequestDto.getTargetPeriod());
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

    @Override
    public void addSugarLevelData(UUID userId, SugarLevelDto sugarLevelDto) {
        Diabetes diabetes = new Diabetes();
        diabetes.setSugarLevel(sugarLevelDto.getSugarLevel());
        diabetes.setDateTime(LocalDateTime.now());
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);
        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            diabetes.setHealth(health);
            health.getDiabetes().add(diabetes);
            diabetesRepository.save(diabetes);
            healthRepository.save(health);
        }
    }

    @Override
    public void addWeight(UUID userID, WeightRequestDto weightRequestDto) {
        Weight weight = new Weight();
        weight.setWeightInKg(weightRequestDto.getWeightInKg());
        weight.setDateTime(LocalDateTime.now());
        Optional<Health> healthOptional = healthRepository.findByUserId(userID);
        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            weight.setHealth(health);
            health.getWeights().add(weight);
            weightRepository.save(weight);
            healthRepository.save(health);
        }
    }

    @Override
    public void addDisease(UUID userID, DiseaseRequestDto diseaseRequestDto) {
        Disease disease = new Disease();
        disease.setDiseaseName(diseaseRequestDto.getDiseaseName());
        disease.setNote(diseaseRequestDto.getNote());
        disease.setDate(LocalDate.now());
        Optional<Health> healthOptional = healthRepository.findByUserId(userID);
        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            disease.setHealth(health);
            health.getDiseases().add(disease);
            diseaseRepository.save(disease);
            healthRepository.save(health);
        }
    }

    @Override
    public void addSmokerAllergyData(UUID userId, SmokerAllergyRequestDto smokerAllergyRequestDto) {
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);
        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            health.setSmokingStatus(smokerAllergyRequestDto.getIsSmoker());
            health.setAllergies(smokerAllergyRequestDto.getAllergies());
            healthRepository.save(health);
        }
    }

}
