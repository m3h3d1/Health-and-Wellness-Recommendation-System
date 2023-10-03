package com.healthapp.userservice.service.impl;

import com.healthapp.userservice.domain.Profile;
import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.model.ProfileRequestDto;
import com.healthapp.userservice.model.ProfileResponseDto;
import com.healthapp.userservice.model.ProfileUpdateDto;
import com.healthapp.userservice.repository.ProfileRepository;
import com.healthapp.userservice.repository.UserRepository;
import com.healthapp.userservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Override
    public void addProfile(ProfileRequestDto profileRequestDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(profileRequestDto.getUserId());
        if(optionalUser.isPresent()) {
            Profile profile = new Profile();
            profile.setUserId(profileRequestDto.getUserId());
            profile.setGender(profileRequestDto.getGender());
            profile.setBloodGroup(profileRequestDto.getBloodGroup());
            profile.setDateOfBirth(profileRequestDto.getDateOfBirth());
            profile.setVegetarian(profileRequestDto.getVegetarian());
            profile.setGoalWeight(profileRequestDto.getGoalWeight());
            profile.setTargetPeriod(profileRequestDto.getTargetPeriod());
            optionalUser.get().setProfile(profile);
            userRepository.save(optionalUser.get());
            profileRepository.save(profile);
        }
    }

    @Override
    public void updateProfile(ProfileUpdateDto profileUpdateDto, UUID userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isPresent()) {
            profileRepository.findByUserId(userId).ifPresent(profile -> {
                if (profileUpdateDto.getGender() != null) {
                    profile.setGender(profileUpdateDto.getGender());
                }
                if (profileUpdateDto.getBloodGroup() != null) {
                    profile.setBloodGroup(profileUpdateDto.getBloodGroup());
                }
                if (profileUpdateDto.getVegetarian() != null) {
                    profile.setVegetarian(profileUpdateDto.getVegetarian());
                }
                if (profileUpdateDto.getDateOfBirth() != null) {
                    profile.setBloodGroup(profileUpdateDto.getBloodGroup());
                }
                if (profileUpdateDto.getGoalWeight() != null) {
                    profile.setGoalWeight(profileUpdateDto.getGoalWeight());
                }
                if (profileUpdateDto.getTargetPeriod() != null) {
                    profile.setTargetPeriod(profileUpdateDto.getTargetPeriod());
                }
                user.get().setProfile(profile);
                userRepository.save(user.get());
                profileRepository.save(profile);
            });
        }
    }

    @Override
    public ProfileResponseDto findById(UUID userId) {
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
        return null;
    }

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
}
