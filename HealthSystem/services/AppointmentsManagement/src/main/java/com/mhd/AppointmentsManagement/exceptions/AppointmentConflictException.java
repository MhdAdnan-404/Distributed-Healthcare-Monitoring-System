package com.mhd.AppointmentsManagement.exceptions;

import com.mhd.AppointmentsManagement.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AppointmentConflictException extends RuntimeException {
    private final List<Appointment> conflicts;
    public AppointmentConflictException(String msg, List<Appointment> conflicts) {
        super(msg);
        this.conflicts = conflicts;
    }
    public List<Appointment> getConflicts(){
        return conflicts;
    }
}
