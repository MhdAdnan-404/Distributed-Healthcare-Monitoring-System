package com.mhd.accountManagement.exception;

public class UserDosentExistException extends RuntimeException {
    public UserDosentExistException(String message) {
        super(message);
    }
}
