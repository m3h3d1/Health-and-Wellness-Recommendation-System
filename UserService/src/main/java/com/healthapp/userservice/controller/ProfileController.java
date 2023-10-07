package com.healthapp.userservice.controller;

import com.healthapp.userservice.domain.Profile;
import com.healthapp.userservice.model.Requestdto.ProfileRequestDto;
import com.healthapp.userservice.model.Responsedto.ProfileResponseDto;
import com.healthapp.userservice.model.updatedeletedto.ProfileUpdateDto;
import com.healthapp.userservice.service.interfaces.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping("/create")
    public ResponseEntity<String> addProfile(@RequestBody ProfileRequestDto profileRequestDto) {
        profileService.addProfile(profileRequestDto);
        return new ResponseEntity<>("Profile Created Successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(
            @RequestBody ProfileUpdateDto profileUpdateDto
    ) {
        profileService.updateProfile(profileUpdateDto);
        return new ResponseEntity<>("Profile Updated Successfully!",HttpStatus.OK);
    }

    @GetMapping("/read-by-id/{userId}")
    public ResponseEntity<ProfileResponseDto> getProfileById(@PathVariable UUID userId) {
        ProfileResponseDto profileResponseDto = profileService.findById(userId);
        if (profileResponseDto != null) {
            return new ResponseEntity<>(profileResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<Profile>> getAllProfile(){
        return new ResponseEntity<>(profileService.getAllProfiles(),HttpStatus.OK);
    }
}
