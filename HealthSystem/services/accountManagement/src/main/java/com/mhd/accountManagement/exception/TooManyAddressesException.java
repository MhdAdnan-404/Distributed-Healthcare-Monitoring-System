package com.mhd.accountManagement.exception;

public class TooManyAddressesException extends RuntimeException {
    public TooManyAddressesException(String message) {
        super(message);
    }
}
