package com.mhd.accountManagement.exception;

public class InvalidActivationTokenException extends RuntimeException {
    public InvalidActivationTokenException(String message){
        super(message);
    }
}
