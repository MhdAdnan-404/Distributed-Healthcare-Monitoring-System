package com.example.Pharmacy.Exception;

public class PrescriptionNotFoundException extends RuntimeException {
    public PrescriptionNotFoundException(String msg) {
        super(msg);
    }
}
