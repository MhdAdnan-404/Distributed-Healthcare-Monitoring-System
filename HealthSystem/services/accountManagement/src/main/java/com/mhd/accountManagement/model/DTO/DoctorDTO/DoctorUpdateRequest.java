package com.mhd.accountManagement.model.DTO.DoctorDTO;


import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.Enums.DoctorSpecialty;
import com.mhd.accountManagement.model.Enums.PrefferedLanguage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

@Builder
public record DoctorUpdateRequest(
        @NotNull(message = "Doctor Id can't be null")
        @Min(value = 1, message = "Doctor ID must be greater than 0")
        Integer id,
        @Pattern(regexp = "^[^\\d]*$", message = "Doctor name cannot contain numbers")
        @NotNull(message = "Doctor name can't be null")
        @NotEmpty(message = "Doctor name can't be null")
        String name,
        @NotNull(message = "Doctor Specialty is requried")
        DoctorSpecialty specialty,
        @NotNull(message = "Address info is required")
        @Valid
        ContactInfoDTO contactInfo,
        @Valid
        PrefferedLanguage language,
        @Size(max = 3, message = "You can provide at most 3 addresses")
        @NotNull(message = "address can't be null")
        List<AddressDTO> addresses
) {
}
