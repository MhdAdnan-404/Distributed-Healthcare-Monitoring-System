package com.example.notification.exceptions;

public class MessageTemplateNotFoundException extends RuntimeException {
    public MessageTemplateNotFoundException(String msg) {
        super(msg);
    }
}
