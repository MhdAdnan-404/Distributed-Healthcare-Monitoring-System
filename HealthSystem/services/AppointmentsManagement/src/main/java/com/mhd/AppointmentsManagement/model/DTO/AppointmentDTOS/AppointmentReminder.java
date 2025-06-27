package com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AppointmentReminder(
        Long appointmentId,
        Integer patientId,
        String patientName,
        String patientToken,
        Integer doctorId,
        String doctorName,
        String doctorToken,
        LocalDateTime appointmentStartTime

) {
}
