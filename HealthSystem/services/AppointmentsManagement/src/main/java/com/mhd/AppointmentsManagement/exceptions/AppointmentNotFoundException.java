package com.mhd.AppointmentsManagement.exceptions;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String msg) {
        super(msg);
    }
}
