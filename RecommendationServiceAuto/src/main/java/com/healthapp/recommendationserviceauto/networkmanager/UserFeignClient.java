package com.healthapp.recommendationserviceauto.networkmanager;

import com.healthapp.recommendationserviceauto.model.ProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name="user-app")
public interface UserFeignClient {
    @GetMapping("/users/profile/read-by-id/{userId}")
    ResponseEntity<ProfileResponseDto> getProfileInfo(@PathVariable UUID userId);

}
