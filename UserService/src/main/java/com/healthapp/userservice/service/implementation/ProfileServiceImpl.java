package com.healthapp.userservice.service.implementation;

import com.healthapp.userservice.domain.BloodGroupEnum;
import com.healthapp.userservice.domain.Profile;
import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.exception.EmptyResultException;
import com.healthapp.userservice.exception.ProfileUpdateException;
import com.healthapp.userservice.exception.UnauthorizedUserException;
import com.healthapp.userservice.model.Requestdto.HealthRequestDto;
import com.healthapp.userservice.model.Requestdto.ProfileRequestDto;
import com.healthapp.userservice.model.Responsedto.ProfileResponseDto;
import com.healthapp.userservice.model.updatedeletedto.ProfileUpdateDto;
import com.healthapp.userservice.networkmanager.HealthFeignClient;
import com.healthapp.userservice.repository.ProfileRepository;
import com.healthapp.userservice.repository.UserRepository;
import com.healthapp.userservice.service.interfaces.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HealthFeignClient healthFeignClient;
    @Override
    public void addProfile(ProfileRequestDto profileRequestDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(profileRequestDto.getUserId());
        if(optionalUser.isPresent()) {
            Profile profile = new Profile();
            profile.setUserId(profileRequestDto.getUserId());
            profile.setGender(profileRequestDto.getGender());
            BloodGroupEnum bloodGroup = BloodGroupEnum.fromString(profileRequestDto.getBloodGroup());
            profile.setBloodGroup(bloodGroup);
            profile.setDateOfBirth(profileRequestDto.getDateOfBirth());
            profile.setVegetarian(profileRequestDto.getVegetarian());
            profile.setGoalWeight(profileRequestDto.getGoalWeight());
            profile.setTargetPeriod(profileRequestDto.getTargetPeriod());

            //Add Data in Health Table
            HealthRequestDto healthRequestDto = new HealthRequestDto();
            healthRequestDto.setUserId(profileRequestDto.getUserId());
            healthRequestDto.setGender(profileRequestDto.getGender().toString());
            healthRequestDto.setDateOfBirth(profileRequestDto.getDateOfBirth());
            healthRequestDto.setGoalWeight(profileRequestDto.getGoalWeight());
            healthRequestDto.setTargetPeriod(profileRequestDto.getTargetPeriod());
            ResponseEntity<String> response=healthFeignClient.addHealthInfo(profileRequestDto.getUserId(),healthRequestDto);

            profileRepository.save(profile);
            optionalUser.get().setProfile(profile);
            userRepository.save(optionalUser.get());
        }
        else{
            throw new EmptyResultException();
        }
    }

    @Override
    public void updateProfile(ProfileUpdateDto profileUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findById(UUID.fromString(authentication.getName()));
        user.ifPresent(userEntity -> profileRepository.findByUserId(UUID.fromString(
                authentication.getName())).ifPresent(profile -> {
            try {
                if (profileUpdateDto.getGender() != null) {
                    profile.setGender(profileUpdateDto.getGender());
                }
                if (profileUpdateDto.getBloodGroup() != null) {
                    BloodGroupEnum bloodGroup = BloodGroupEnum.fromString(profileUpdateDto.getBloodGroup());
                    profile.setBloodGroup(bloodGroup);
                }
                if (profileUpdateDto.getVegetarian() != null) {
                    profile.setVegetarian(profileUpdateDto.getVegetarian());
                }
                if (profileUpdateDto.getDateOfBirth() != null) {
                    profile.setDateOfBirth(profileUpdateDto.getDateOfBirth());
                }
                if (profileUpdateDto.getGoalWeight() != null) {
                    profile.setGoalWeight(profileUpdateDto.getGoalWeight());
                }
                if (profileUpdateDto.getTargetPeriod() != null) {
                    profile.setTargetPeriod(profileUpdateDto.getTargetPeriod());
                }
                profileRepository.save(profile);
                userEntity.setProfile(profile);
                userRepository.save(userEntity);

                // Update health data using the Feign client
                healthFeignClient.updateHealthInfo(
                        UUID.fromString(authentication.getName()),
                        new ProfileUpdateDto(
                                profile.getGender(),
                                profile.getDateOfBirth(),
                                profile.getBloodGroup().toString(),
                                profile.getVegetarian(),
                                profile.getGoalWeight(),
                                profile.getTargetPeriod()
                        )
                );
            } catch (Exception ex) {
                throw new ProfileUpdateException();
            }
        }));
    }


    @Override
    public ProfileResponseDto findById(UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID authenticatedUserId = UUID.fromString(authentication.getName());
        if(authenticatedUserId != userId){
            throw new UnauthorizedUserException();
        }
        Optional<Profile> optionalProfile=profileRepository.findByUserId(userId);
        if(optionalProfile.isPresent()){
            Profile profile = optionalProfile.get();
            ProfileResponseDto responseDto = new ProfileResponseDto();
            responseDto.setGender(profile.getGender());
            responseDto.setBloodGroup(profile.getBloodGroup());
            responseDto.setDateOfBirth(profile.getDateOfBirth());
            responseDto.setVegetarian(profile.getVegetarian());
            responseDto.setGoalWeight(profile.getGoalWeight());
            responseDto.setTargetPeriod(profile.getTargetPeriod());
            return responseDto;
        }
        else{
            throw new EmptyResultException();
        }
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
}
