package com.mhd.accountManagement.model.DTO.DoctorDTO;

import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.UserRegisterRequestDTO;
import com.mhd.accountManagement.model.Enums.DoctorSpecialty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DoctorRegisterRequest(
        @NotNull(message = "Doctor name can't be empty")
        @NotEmpty(message = "Doctor name can't be null")
        @Pattern(regexp = "^[^\\d]*$", message = "Doctor name cannot contain numbers")
        String name,
        @NotNull(message = "Doctor Specialty can't be null")
        DoctorSpecialty specialty,

        @NotNull(message = "User info can't be null")
        @Valid
        UserRegisterRequestDTO user

) {
}
