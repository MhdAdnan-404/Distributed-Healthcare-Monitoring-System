package com.mhd.accountManagement.model.DTO.UserDTO;

import com.mhd.accountManagement.model.Enums.PrefferedLanguage;
import com.mhd.accountManagement.model.Enums.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record UserRegisterRequestDTO(
        @NotNull(message = "Username can't be empty")
        @NotNull(message = "Username name can't be empty")
        @NotEmpty(message = "Username name can't be null")
        String username,
        @NotNull(message = "password can't be empty")
        @NotEmpty(message = "password name can't be null")
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$",
                message = "Password must be at least 8 characters long and include an uppercase letter, a lowercase letter, a number, and a special character"
        )
        String password,
        @Size(max = 3, message = "You can provide at most 3 addresses")
        List<AddressDTO> addresses,
        @NotNull(message = "contact Info can't be null")
        @Valid
        ContactInfoDTO contactInfoDTO,
        PrefferedLanguage language,
        @NotNull(message = "role can't be null")
        Role role
) {
}
