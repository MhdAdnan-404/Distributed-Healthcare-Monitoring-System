package com.example.notification.exceptions;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class exceptionHandler {

    @ExceptionHandler(TopicNotFoundException.class)
    public ResponseEntity<String> handle(TopicNotFoundException exp){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exp.getMessage());
    }

    @ExceptionHandler(MessageTemplateNotFoundException.class)
    public ResponseEntity<String> handle(MethodArgumentNotValidException exp){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exp.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handle(UserAlreadyExistsException exp){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exp.getMessage());
    }

    @ExceptionHandler(UserDosentExistException.class)
    public ResponseEntity<String> handle(UserDosentExistException exp){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exp.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidations(MethodArgumentNotValidException exp){
        Map<String, String> errors = new HashMap<>();

        String allErrorMessages = exp.getBindingResult().getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        if (allErrorMessages.isEmpty()) {
            allErrorMessages = "Validation failed: no specific error messages available.";
        }

        return new ResponseEntity<>(allErrorMessages, HttpStatus.BAD_REQUEST);
    }
}
