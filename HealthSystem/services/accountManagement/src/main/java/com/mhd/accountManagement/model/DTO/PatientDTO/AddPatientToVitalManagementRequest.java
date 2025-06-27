package com.mhd.accountManagement.model.DTO.PatientDTO;


import lombok.Builder;

@Builder
public record AddPatientToVitalManagementRequest(
        Integer id,
        String name
) {
}
