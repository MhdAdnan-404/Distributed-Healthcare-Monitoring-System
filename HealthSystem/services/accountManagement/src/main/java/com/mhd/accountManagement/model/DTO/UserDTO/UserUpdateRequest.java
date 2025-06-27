package com.mhd.accountManagement.model.DTO.UserDTO;

import com.mhd.accountManagement.model.Enums.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
        @NotNull(message = "User Id can't be null")
        Integer userId,
        @NotNull(message="Username can't be null")
        @Pattern(regexp = "^[^\\d]*$", message = "Patient name cannot contain numbers")
        String username,
        @NotNull(message = "Role Can't be null")
        Role role
) {
}
