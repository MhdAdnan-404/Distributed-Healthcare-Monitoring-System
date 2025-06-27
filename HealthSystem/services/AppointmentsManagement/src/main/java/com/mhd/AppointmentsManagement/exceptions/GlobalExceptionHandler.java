package com.mhd.AppointmentsManagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(AppointmentConflictException.class)
    public ResponseEntity<Map<String,Object>> handle(AppointmentConflictException exp){
        Map<String, Object> response = new HashMap<>();
        response.put("message", exp.getMessage());
        response.put("conflicts", exp.getConflicts());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<String> hanle(AppointmentNotFoundException exp){
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
