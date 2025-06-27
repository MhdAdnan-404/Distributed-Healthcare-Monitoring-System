package com.example.notification.exceptions;

public class UserDosentExistException extends RuntimeException {
    public UserDosentExistException(String msg) {
        super(msg);
    }
}
