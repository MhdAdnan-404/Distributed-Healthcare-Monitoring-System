package com.mhd.AppointmentsManagement.repository.Appointment;

import com.mhd.AppointmentsManagement.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentCustomRepository {
    List<Appointment> findConflictingAppointments(
            Integer doctorId,
            LocalDateTime requestedStart,
            LocalDateTime requestedEnd,
            Long excludedAppointmentId
    );
}
