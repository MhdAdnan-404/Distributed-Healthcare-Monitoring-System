package com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS;

import com.mhd.AppointmentsManagement.model.Enums.AppointmentStatues;
import lombok.Builder;

@Builder
public record AppointmentCancellation(
        Long appointmentId,
        Integer patientId,
        String patientName,
        Integer doctorId,
        String doctorName,
        AppointmentStatues appointmentStatues
) {
}
