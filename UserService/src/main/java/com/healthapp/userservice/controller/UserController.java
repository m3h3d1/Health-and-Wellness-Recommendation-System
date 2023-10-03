package com.healthapp.userservice.controller;

import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.model.*;
import com.healthapp.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDto userRequestDto) {
        userService.registerUser(userRequestDto);
        return new ResponseEntity<>("User Created Successfully!",HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDto userUpdateDto, @PathVariable UUID userId) {
        userService.updateUser(userUpdateDto, userId);
        return new ResponseEntity<>("User Updated Successfully!",HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@RequestBody UserDeleteDto userDeleteDto, @PathVariable UUID userId) {
        userService.deleteUser(userDeleteDto);
        return new ResponseEntity<>("User Deleted Successfully",HttpStatus.NO_CONTENT);
    }
    @GetMapping("/read-by-id/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID userId) {
        UserResponseDto userResponseDto = userService.getUserById(userId);
        if (userResponseDto != null) {
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
    @PutMapping("/change-password/{userId")
    public ResponseEntity<String> changePassword(
            @PathVariable UUID userId,
            @RequestBody ChangePasswordDto changePasswordDto) {
        userService.changePassword(changePasswordDto, userId);
        return new ResponseEntity<>("Password changed successfully!", HttpStatus.OK);
    }
    @PostMapping("/assign-role/{userId}")
    public ResponseEntity<String> assignRole(
            @PathVariable UUID userId,
            @RequestBody AssignRoleDto assignRoleDto) {
        userService.assignRole(assignRoleDto, userId);
        return new ResponseEntity<>("User role assigned successfully", HttpStatus.OK);
    }
    @DeleteMapping("/remove-role/{userId}")
    public ResponseEntity<String> removeRole(@PathVariable UUID userId) {
        userService.removeRole(userId);
        return new ResponseEntity<>("User role removed successfully!", HttpStatus.OK);
    }
}
