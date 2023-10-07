package com.healthapp.userservice.service.interfaces;

import com.healthapp.userservice.domain.Profile;
import com.healthapp.userservice.model.Requestdto.ProfileRequestDto;
import com.healthapp.userservice.model.Responsedto.ProfileResponseDto;
import com.healthapp.userservice.model.updatedeletedto.ProfileUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ProfileService {
    void addProfile(ProfileRequestDto profileRequestDto);
    void updateProfile(ProfileUpdateDto profileUpdateDto);
    ProfileResponseDto findById(UUID userId);
    List<Profile> getAllProfiles();
}
