package com.mhd.accountManagement.exception;

public class UserHasBeenDeletedException extends RuntimeException {
    public UserHasBeenDeletedException(String message) {
        super(message);
    }
}
