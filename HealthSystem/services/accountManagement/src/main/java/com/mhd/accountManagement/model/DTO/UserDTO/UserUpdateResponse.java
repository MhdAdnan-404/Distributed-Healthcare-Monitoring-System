package com.mhd.accountManagement.model.DTO.UserDTO;

import com.mhd.accountManagement.model.Enums.Role;

public record UserUpdateResponse(Integer id, String username, Role role) {
}

