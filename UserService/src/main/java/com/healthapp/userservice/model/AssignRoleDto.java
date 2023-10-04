package com.healthapp.userservice.model;

import com.healthapp.userservice.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignRoleDto {
    private UserEntity.Roles role;
}
