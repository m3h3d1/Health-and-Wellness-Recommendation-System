package com.healthapp.userservice.service;

import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.model.*;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void registerUser(UserRequestDto userRequestDto);
    void updateUser(UserUpdateDto userUpdateDto, UUID userId);
    void deleteUser(UserDeleteDto userDeleteDto);
    UserResponseDto getUserById(UUID userId);
    List<UserEntity> getAllUsers();
    void changePassword(ChangePasswordDto changePasswordDto, UUID userId);
    void assignRole(AssignRoleDto assignRoleDto, UUID userId);
    void removeRole(UUID userId);
}
