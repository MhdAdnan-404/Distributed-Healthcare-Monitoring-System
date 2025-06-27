package com.mhd.accountManagement.model.DTO.PatientDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.UserRegisterRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
@Builder
public record PatientRegisterRequest(
        @Pattern(regexp = "^[^\\d]*$", message = "Patient name cannot contain numbers")
        @NotNull(message = "Patient name can't be null")
        @NotEmpty(message = "Patient name can't be null")
        String name,
        @NotNull(message = "Date of birth can't be empty")
        @Past(message = "Date of birth must be in the past")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate dateOfBirth,
        @NotNull(message="Allergies can't be null")
        String allergies,
        @NotNull(message = "User info can't be null")
        @Valid
        UserRegisterRequestDTO user
) {
}
