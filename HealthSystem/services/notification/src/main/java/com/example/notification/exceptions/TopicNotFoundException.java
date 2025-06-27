package com.example.notification.exceptions;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String msg) {
        super(msg);
    }
}
