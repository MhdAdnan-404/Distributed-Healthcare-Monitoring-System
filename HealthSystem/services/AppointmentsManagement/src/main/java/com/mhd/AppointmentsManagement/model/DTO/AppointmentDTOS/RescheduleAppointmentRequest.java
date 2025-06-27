package com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS;

import com.mhd.AppointmentsManagement.validation.NotInPast;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RescheduleAppointmentRequest(
        @NotNull(message = "Appointment ID can't be null")
        @Min(value = 1, message = "Appointment ID must be a positive number")
        Long appointmentId,

        @NotNull(message = "Duration can't be null")
        @Min(value = 1, message = "Duration must be a positive number")
        Long durationInMinutes,

        @NotNull(message = "New Start Time can't be null")
        @NotInPast
        LocalDateTime newDateAndTime
) {
}
