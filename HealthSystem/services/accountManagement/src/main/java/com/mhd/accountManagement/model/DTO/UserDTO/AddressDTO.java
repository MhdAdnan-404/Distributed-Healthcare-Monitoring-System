package com.mhd.accountManagement.model.DTO.UserDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AddressDTO(
        Integer id,
        @NotNull(message = "country name can't be null")
        @NotEmpty(message = "country name can't be empty")
        @Pattern(regexp = "^[A-Za-z\\s\\-]+$", message = "Field must contain only letters, spaces, or hyphens")
        String country,
        @NotNull(message = "city name can't be null")
        @NotEmpty(message = "city name can't be empty")
        @Pattern(regexp = "^[A-Za-z\\s\\-]+$", message = "Field must contain only letters, spaces, or hyphens")
        String city,
        @NotNull(message = "streetName name can't be null")
        @NotEmpty(message = "streetName name can't be empty")
        String streetName,
        @NotNull(message = "streetNumber name can't be null")
        @NotEmpty(message = "streetNumber name can't be empty")
        String streetNumber,
        @NotNull(message = "label name can't be null")
        @NotEmpty(message = "label name can't be empty")
        String label
) {
}
