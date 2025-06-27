package com.mhd.accountManagement.exception;

public class UserHasNotBeenActivatedException extends RuntimeException {
    public UserHasNotBeenActivatedException(String message) {
        super(message);
    }
}
