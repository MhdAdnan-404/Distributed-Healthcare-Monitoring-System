package com.mhd.vitalSignManagement.handler.exception;

public class DeviceHasAlreadyBeenAddedToThisPatientException extends RuntimeException {
    public DeviceHasAlreadyBeenAddedToThisPatientException(String message) {
        super(message);
    }
}
