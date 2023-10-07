package com.healthapp.userservice.model.Requestdto;

import com.healthapp.userservice.domain.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignRoleDto {
    private RoleEnum role;
}
