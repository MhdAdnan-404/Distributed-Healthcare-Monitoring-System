package com.mhd.accountManagement.model.DTO.PatientDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.Enums.PrefferedLanguage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PatientUpdateRequest(
        @NotNull(message = "patient Id can't be null")
        @Min(value = 1, message = "Patient ID must be greater than 0")
        Integer id,
        @Pattern(regexp = "^[^\\d]*$", message = "Patient name cannot contain numbers")
        @NotNull(message = "Patient name can't be null")
        @NotEmpty(message = "Patient name can't be null")
        String name,
        @NotNull(message = "Date of birth can't be empty")
        @Past(message = "Date of birth must be in the past")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,
        @Valid
        ContactInfoDTO contactInfo,
        @Valid
        PrefferedLanguage language,
        @Size(max = 3, message = "You can provide at most 3 addresses")
        @NotNull(message = "address can't be null")
        List<AddressDTO> addresses,

        @NotNull(message="Allergies can't be null")
        String allergies
) {
}
