package com.healthapp.userservice.service.implementation;

import com.healthapp.userservice.domain.Role;
import com.healthapp.userservice.domain.RoleEnum;
import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.exception.EmptyResultException;
import com.healthapp.userservice.exception.PasswordException;
import com.healthapp.userservice.exception.UserUpdateException;
import com.healthapp.userservice.model.Requestdto.*;
import com.healthapp.userservice.model.Responsedto.UserResponseDto;
import com.healthapp.userservice.model.updatedeletedto.UserDeleteDto;
import com.healthapp.userservice.model.updatedeletedto.UserRequestDto;
import com.healthapp.userservice.model.updatedeletedto.UserUpdateDto;
import com.healthapp.userservice.repository.UserRepository;
import com.healthapp.userservice.service.interfaces.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if(userRequestDto.getPassword().length()<5){
            throw new PasswordException();
        }
        userEntity.setPassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));
        userEntity.setEmail(userRequestDto.getEmail());
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(RoleEnum.USER.toString()));

        // Set the admin role when username equals admin...
        if(userEntity.getUserName().toLowerCase().equals("admin")){
            roles.add(new Role(RoleEnum.ADMIN.toString()));
        }

        userEntity.setRoles(roles);
        userRepository.save(userEntity);
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.findById(UUID.fromString(authentication.getName())).ifPresent(user -> {
            try {
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
            } catch (Exception ex) {
                throw new UserUpdateException();
            }
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
            responseDto.setRoles(user.getRoles());
            responseDto.setEmail(user.getEmail());
            responseDto.setContact(user.getContact());
            responseDto.setProfile(user.getProfile());
            return responseDto;
        }
        else{
            throw new EmptyResultException();
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void changePassword(ChangePasswordDto changePasswordDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> optionalUser=userRepository.findById(UUID.fromString(authentication.getName()));
        if(optionalUser.isPresent()){
            UserEntity user=optionalUser.get();
            if(user.getPassword().equals(changePasswordDto.getOldPassword())){
                user.setPassword(changePasswordDto.getNewPassword());
                userRepository.save(user);
            }
        }
        else{
            throw new EmptyResultException();
        }
    }

    @Override
    public void assignRole(AssignRoleDto assignRoleDto, UUID userId) {
        Optional<UserEntity> optionalUser= userRepository.findById(userId);
        if(optionalUser.isPresent()){
            UserEntity user=optionalUser.get();
            user.getRoles().add(new Role(assignRoleDto.getRole().toString()));
            userRepository.save(user);
        }
        else{
            throw new EmptyResultException();
        }
    }

    @Override
    public void removeRole(UUID userId) {
        Optional<UserEntity> optionalUser= userRepository.findById(userId);
        optionalUser.ifPresent(userEntity -> userEntity.setRoles(null));
    }
     @Override
     public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         Optional<UserEntity> user = userRepository.findByEmail(email);
         List<GrantedAuthority> roles = new ArrayList<>();
         for(Role role: user.get().getRoles()){
             roles.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
         }
     return new org.springframework.security.core.userdetails.User(user.get().getUserId().toString(), user.get().getPassword(),
            true, true, true, true,
            roles);
}
}
