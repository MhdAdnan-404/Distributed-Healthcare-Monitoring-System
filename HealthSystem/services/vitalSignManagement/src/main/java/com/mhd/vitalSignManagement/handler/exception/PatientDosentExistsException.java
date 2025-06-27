package com.mhd.vitalSignManagement.handler.exception;

public class PatientDosentExistsException extends RuntimeException{
    public PatientDosentExistsException(String message) {
        super(message);
    }
}
