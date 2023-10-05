package com.healthapp.userservice.service;

import com.healthapp.userservice.domain.Profile;
import com.healthapp.userservice.model.ProfileRequestDto;
import com.healthapp.userservice.model.ProfileResponseDto;
import com.healthapp.userservice.model.ProfileUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ProfileService {
    void addProfile(ProfileRequestDto profileRequestDto);
    void updateProfile(ProfileUpdateDto profileUpdateDto, UUID userId);
    ProfileResponseDto findById(UUID userId);
    List<Profile> getAllProfiles();
}
