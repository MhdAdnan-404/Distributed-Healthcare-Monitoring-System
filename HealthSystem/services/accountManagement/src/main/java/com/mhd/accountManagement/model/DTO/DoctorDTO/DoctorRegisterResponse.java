package com.mhd.accountManagement.model.DTO.DoctorDTO;

import com.mhd.accountManagement.model.Enums.ApprovalStatues;
import com.mhd.accountManagement.model.Enums.DoctorSpecialty;

public record DoctorRegisterResponse(
        Integer id,
        String token

) {
}
