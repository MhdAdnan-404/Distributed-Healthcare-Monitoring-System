package com.mhd.accountManagement.model.DTO.DoctorDTO;

import com.mhd.accountManagement.model.ContactInfo;
import com.mhd.accountManagement.model.DTO.UserDTO.AddressDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.Enums.DoctorSpecialty;

import java.util.List;


public record DoctorUpdateResponse(
        Integer id,
        String name,
        DoctorSpecialty specialty,
        ContactInfoDTO contactInfo,
        List<AddressDTO> addresses
) {
}
