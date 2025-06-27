package com.mhd.accountManagement.model.DTO.UserDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
        @NotNull(message = "Username Can't be empty")
        String username,

        @NotNull(message = "Password can't be empty")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@!#$%^&+=()\\-])[A-Za-z\\d@!#$%^&+=()\\-]{8,}$",
                message = "Password must be at least 8 characters and include uppercase, lowercase, number, and special character"
        )
        String password
        ) {
}
