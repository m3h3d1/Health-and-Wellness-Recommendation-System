package com.healthapp.userservice.service.impl;

import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.model.*;
import com.healthapp.userservice.repository.UserRepository;
import com.healthapp.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void registerUser(UserRequestDto userRequestDto) {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserName(userRequestDto.getUserName());
        userEntity.setFirstName(userRequestDto.getFirstName());
        userEntity.setLastName(userRequestDto.getLastName());
        userEntity.setPassword(userRequestDto.getPassword());
        userEntity.setEmail(userRequestDto.getEmail());
        userRepository.save(userEntity);
    }

    @Override
    public void updateUser(UserUpdateDto userUpdateDto, UUID userId) {
        userRepository.findById(userId).ifPresent(user -> {
            if (userUpdateDto.getFirstName() != null) {
                user.setFirstName(userUpdateDto.getFirstName());
            }
            if (userUpdateDto.getLastName() != null) {
                user.setLastName(userUpdateDto.getLastName());
            }
            if (userUpdateDto.getUserName() != null) {
                user.setUserName(userUpdateDto.getUserName());
            }
            if (userUpdateDto.getEmail() != null) {
                user.setEmail(userUpdateDto.getEmail());
            }
            userRepository.save(user);
        });
    }

    @Override
    public void deleteUser(UserDeleteDto userDeleteDto) {
        userRepository.deleteById(userDeleteDto.getUserId());
    }

    @Override
    public UserResponseDto getUserById(UUID userId) {
        Optional<UserEntity> optionalUser=userRepository.findById(userId);
        if(optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            UserResponseDto responseDto = new UserResponseDto();
            responseDto.setUserName(user.getUserName());
            responseDto.setFirstName(user.getFirstName());
            responseDto.setLastName(user.getLastName());
            responseDto.setEmail(user.getEmail());
            return responseDto;
        }
        return null;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto, UUID userId) {
        Optional<UserEntity> optionalUser=userRepository.findById(userId);
        if(optionalUser.isPresent()){
            UserEntity user=optionalUser.get();
            if(user.getPassword().equals(changePasswordDto.getOldPassword())){
                user.setPassword(changePasswordDto.getNewPassword());
                userRepository.save(user);
            }
        }
    }

    @Override
    public void assignRole(AssignRoleDto assignRoleDto, UUID userId) {
        Optional<UserEntity> optionalUser= userRepository.findById(userId);
        if(optionalUser.isPresent()){
            UserEntity user=optionalUser.get();
            user.setRoles(assignRoleDto.getRole());
            userRepository.save(user);
        }
    }

    @Override
    public void removeRole(UUID userId) {
        Optional<UserEntity> optionalUser= userRepository.findById(userId);
        if(optionalUser.isPresent()){
            optionalUser.get().setRoles(null);
        }
    }
}
