package com.mhd.accountManagement.model.DTO.UserDTO;

import com.mhd.accountManagement.model.Enums.Role;

public record LoginResponse(
        String token,
        Role role
) {
}
