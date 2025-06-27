package com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS;

import com.mhd.AppointmentsManagement.model.Enums.AppointmentStatues;
import jakarta.validation.constraints.NotNull;

public record updateAppointmentStatusRequest(
        @NotNull(message = "AppointmentId Can't be null")
        Long Id,

        @NotNull(message = "New Statues Can't be null")
        AppointmentStatues newStatus
) {}
