package com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS;

import com.mhd.AppointmentsManagement.model.Enums.AppointmentType;
import com.mhd.AppointmentsManagement.validation.NotInPast;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

public record CreateAppointmentRequest(
        @NotNull(message = "Doctor Id Can't be null")
        @Min(value = 1, message = "Doctor Id must be a positive number")
        Integer doctorId,
        @NotNull(message = "doctorName  Can't be null")
        @NotEmpty(message = "doctorName Can't be Empty")
        String doctorName,
        @NotNull(message = "Patient Id Can't be null")
        @Min(value = 1, message = "Patient Id must be a positive number")
        Integer patientId,
        @NotNull(message = "patientName  Can't be null")
        @NotEmpty(message = "patientName Can't be Empty")
        String patientName,
        @NotNull(message = "Start time can't be null")
        @NotInPast
        LocalDateTime dateAndTime,
        @NotNull(message = "Duration can't be null")
        @Min(value = 1, message = "Duration must be at least 1 minute")
        Long approxDurationInMinutes,
        @NotNull(message = "Appointment Type Can't be null")
        AppointmentType type


) {
}
