package com.healthapp.userservice.service.interfaces;

import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.model.Requestdto.*;
import com.healthapp.userservice.model.Responsedto.UserResponseDto;
import com.healthapp.userservice.model.updatedeletedto.UserDeleteDto;
import com.healthapp.userservice.model.updatedeletedto.UserRequestDto;
import com.healthapp.userservice.model.updatedeletedto.UserUpdateDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void registerUser(UserRequestDto userRequestDto);
    void updateUser(UserUpdateDto userUpdateDto);
    void deleteUser(UserDeleteDto userDeleteDto);
    UserResponseDto getUserById(UUID userId);
    List<UserEntity> getAllUsers();
    void changePassword(ChangePasswordDto changePasswordDto);
    void assignRole(AssignRoleDto assignRoleDto, UUID userId);
    UserResponseDto getUserByEmail(String email);
    void removeRole(UUID userId);
}
