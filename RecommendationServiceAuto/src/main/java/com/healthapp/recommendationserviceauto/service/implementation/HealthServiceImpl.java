package com.healthapp.recommendationserviceauto.service.implementation;

import com.healthapp.recommendationserviceauto.domain.*;
import com.healthapp.recommendationserviceauto.exception.*;
import com.healthapp.recommendationserviceauto.model.entrydatadto.ActivityRequestDto;
import com.healthapp.recommendationserviceauto.model.requestdto.*;
import com.healthapp.recommendationserviceauto.networkmanager.UserFeignClient;
import com.healthapp.recommendationserviceauto.repository.*;
import com.healthapp.recommendationserviceauto.service.interfaces.HealthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ActivityFactorRepository activityFactorRepository;
    @Autowired
    private HeightRepository heightRepository;
    private int calculateAge(Date dateOfBirth) {
        Date currentDate = new Date();
        long timeDiff = currentDate.getTime() - dateOfBirth.getTime();
        long ageInMillis = timeDiff;
        int ageInYears = (int) (ageInMillis / (1000L * 60 * 60 * 24 * 365));
        return ageInYears;
    }

    @Override
    public Health getHealthData(UUID userId) {
        return healthRepository.findByUserId(userId).get();
    }

    @Override
    public void addHealthData(UUID userId, HealthRequestDto healthRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID authenticatedUserId = UUID.fromString(authentication.getName());
        if(authenticatedUserId != userId){
            throw new UnauthorizedUserException();
        }
        Health health = new Health();
        health.setUserId(authenticatedUserId);
        health.setGender(healthRequestDto.getGender());
        health.setAge(calculateAge(healthRequestDto.getDateOfBirth()));
        health.setGoalWeight(healthRequestDto.getGoalWeight());
        health.setTargetPeriod(healthRequestDto.getTargetPeriod());
        healthRepository.save(health);
    }

    @Override
    public void addBloodPressureData(UUID userId, BloodPressureRequestDto bloodPressureRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID authenticatedUserId = UUID.fromString(authentication.getName());
        if(authenticatedUserId != userId){
            throw new UnauthorizedUserException();
        }
        if (bloodPressureRequestDto.getHighPressure() > 190) {
            throw new BloodPressureException(BloodPressureException.HIGH_PRESSURE_MESSAGE);
        }
        if (bloodPressureRequestDto.getLowPressure() < 50) {
            throw new BloodPressureException(BloodPressureException.LOW_PRESSURE_MESSAGE);
        }

        BloodPressure bloodPressure = new BloodPressure();
        bloodPressure.setLowPressure(bloodPressureRequestDto.getLowPressure());
        bloodPressure.setHighPressure(bloodPressureRequestDto.getHighPressure());
        bloodPressure.setDateTime(LocalDateTime.now());
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);

        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            bloodPressure.setHealth(health);
            health.getBloodPressures().add(bloodPressure);
            bloodPressureRepository.save(bloodPressure);
            healthRepository.save(health);
        }

        throw new EmptyResultException();
    }


    @Override
    public void addSugarLevelData(UUID userId, SugarLevelDto sugarLevelDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID authenticatedUserId = UUID.fromString(authentication.getName());
        if(authenticatedUserId != userId){
            throw new UnauthorizedUserException();
        }
        if (sugarLevelDto.getSugarLevel() > 250.0) {
            throw new SugarLevelException(SugarLevelException.HIGH_SUGAR_MESSAGE);
        }
        if (sugarLevelDto.getSugarLevel() < 50.0) {
            throw new SugarLevelException(SugarLevelException.LOW_SUGAR_MESSAGE);
        }

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

        throw new EmptyResultException();
    }


    @Override
    public void addWeight(UUID userId, WeightRequestDto weightRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID authenticatedUserId = UUID.fromString(authentication.getName());
        if(authenticatedUserId != userId){
            throw new UnauthorizedUserException();
        }

        double weightInKg = weightRequestDto.getWeightInKg();

        if (weightInKg <5.0) {
            throw new WeightException(WeightException.LOW_WEIGHT_MESSAGE);
        }
        if (weightInKg > 250.0) {
            throw new WeightException(WeightException.HIGH_WEIGHT_MESSAGE);
        }

        Weight weight = new Weight();
        weight.setWeightInKg(weightInKg);
        weight.setDateTime(LocalDateTime.now());
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);

        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            weight.setHealth(health);
            health.getWeights().add(weight);
            weightRepository.save(weight);
            healthRepository.save(health);
        }

        throw new EmptyResultException();
    }


    @Override
    public void addHeight(UUID userId, HeightRequestDto heightRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID authenticatedUserId = UUID.fromString(authentication.getName());
        if(authenticatedUserId != userId){
            throw new UnauthorizedUserException();
        }
        Double heightInCm = heightRequestDto.getHeight();

        if (heightInCm < 100) {
            throw new HeightException(HeightException.LOW_HEIGHT_MESSAGE);
        }
        if (heightInCm > 255) {
            throw new HeightException(HeightException.HIGH_HEIGHT_MESSAGE);
        }

        Height height = new Height();
        height.setHeightInCm(heightInCm);
        height.setDateTime(LocalDateTime.now());
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);

        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            height.setHealth(health);
            health.getHeights().add(height);
            heightRepository.save(height);
            healthRepository.save(health);
        }

        throw new EmptyResultException();
    }


    @Override
    public void addDisease(UUID userID, DiseaseRequestDto diseaseRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID authenticatedUserId = UUID.fromString(authentication.getName());
        if(authenticatedUserId != userID){
            throw new UnauthorizedUserException();
        }
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
        throw new EmptyResultException();
    }

    @Override
    public void addActivityData(ActivityRequestDto activityRequestDto) {
        ActivityFactor activityFactor = new ActivityFactor();
        activityFactor.setActivityLevel(activityRequestDto.getActivityLevel());
        activityFactor.setFactor(activityRequestDto.getFactor());
        activityFactorRepository.save(activityFactor);
    }

    @Override
    public void addExtraData(UUID userId, ExtraRequestDto extraRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID authenticatedUserId = UUID.fromString(authentication.getName());
        if(authenticatedUserId != userId){
            throw new UnauthorizedUserException();
        }
        Optional<Health> healthOptional = healthRepository.findByUserId(userId);
        if (healthOptional.isPresent()) {
            Health health = healthOptional.get();
            health.setSmokingStatus(extraRequestDto.getIsSmoker());
            health.setAllergies(extraRequestDto.getAllergies());
            Optional<ActivityFactor> activityFactorOptional = activityFactorRepository.findByActivityLevel(extraRequestDto.getActivityLevel());
            if(activityFactorOptional.isEmpty()){

            }
            health.setDailyActivity(activityFactorOptional.get());
            healthRepository.save(health);
        }
        throw new EmptyResultException();
    }

    @Override
    public void updateHealthData(UUID userId, HealthUpdateRequestDto healthUpdateRequestDto) {
        Optional<Health> existingHealthOptional = healthRepository.findByUserId(userId);
        if (existingHealthOptional.isEmpty()) {
            throw new EmptyResultException();
        }
        Health existingHealth= existingHealthOptional.get();
        if (healthUpdateRequestDto.getGender() != null) {
            existingHealth.setGender(healthUpdateRequestDto.getGender());
        }
        if (healthUpdateRequestDto.getDateOfBirth() != null) {
            existingHealth.setAge(calculateAge(healthUpdateRequestDto.getDateOfBirth()));
        }
        if (healthUpdateRequestDto.getGoalWeight() != null) {
            existingHealth.setGoalWeight(healthUpdateRequestDto.getGoalWeight());
        }
        if (healthUpdateRequestDto.getTargetPeriod() != null) {
            existingHealth.setTargetPeriod(healthUpdateRequestDto.getTargetPeriod());
        }
        healthRepository.save(existingHealth);
    }


}
