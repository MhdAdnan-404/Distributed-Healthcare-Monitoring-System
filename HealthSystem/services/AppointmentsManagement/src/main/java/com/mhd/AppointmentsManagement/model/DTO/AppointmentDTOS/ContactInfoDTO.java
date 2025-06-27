package com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS;

import com.mhd.AppointmentsManagement.model.Enums.ContactType;

import java.util.Map;

public record ContactInfoDTO(
        Integer id,
        Map<ContactType, String> contacts

) {
}
