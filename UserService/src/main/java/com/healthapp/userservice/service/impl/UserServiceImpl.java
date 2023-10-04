package com.healthapp.userservice.service.impl;

import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.model.*;
import com.healthapp.userservice.repository.UserRepository;
import com.healthapp.userservice.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void registerUser(UserRequestDto userRequestDto) {
        UserEntity userEntity=new UserEntity();
        userEntity.setUserName(userRequestDto.getUserName());
        userEntity.setFirstName(userRequestDto.getFirstName());
        userEntity.setLastName(userRequestDto.getLastName());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));
        userEntity.setEmail(userRequestDto.getEmail());
        userEntity.setRoles(UserEntity.Roles.User);
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
        else{
            throw new EmptyResultDataAccessException("User",1);
        }
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
        else{
            throw new EmptyResultDataAccessException("User",1);
        }
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email).get();
        if (user == null) throw new UsernameNotFoundException("No record found");
        UserResponseDto returnValue = new UserResponseDto();
        BeanUtils.copyProperties(user, returnValue);
        return returnValue;
    }

    @Override
    public void removeRole(UUID userId) {
        Optional<UserEntity> optionalUser= userRepository.findById(userId);
        optionalUser.ifPresent(userEntity -> userEntity.setRoles(null));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRoles().name());
        authorities.add(grantedAuthority);
        if (user == null) throw new UsernameNotFoundException(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                true, true, true, true, authorities);
    }
}
