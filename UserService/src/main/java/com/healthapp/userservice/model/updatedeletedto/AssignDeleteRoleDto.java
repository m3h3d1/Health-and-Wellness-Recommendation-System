package com.healthapp.userservice.model.updatedeletedto;

import com.healthapp.userservice.domain.RoleEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AssignDeleteRoleDto {
    private RoleEnum role;
}
