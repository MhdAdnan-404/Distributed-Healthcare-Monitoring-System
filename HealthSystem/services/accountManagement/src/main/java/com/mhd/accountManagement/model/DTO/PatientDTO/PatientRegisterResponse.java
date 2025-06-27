package com.mhd.accountManagement.model.DTO.PatientDTO;

import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;

import java.time.LocalDate;
import java.util.List;

public record PatientRegisterResponse(
        Integer id,
        String activationToken
) {}

