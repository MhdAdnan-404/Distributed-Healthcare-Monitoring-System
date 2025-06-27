package com.mhd.accountManagement.model.DTO.UserDTO;

import com.mhd.accountManagement.model.Enums.Role;
import jakarta.validation.constraints.NotNull;

public record UserResponse(
        Integer id,
        String username,
        Role role,
        Boolean isActivated,
        Boolean isDeleted
) {
}
