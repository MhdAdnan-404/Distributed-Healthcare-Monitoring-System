package com.mhd.vitalSignManagement.handler;

import com.mhd.vitalSignManagement.handler.exception.DeviceHasAlreadyBeenAddedToThisPatientException;
import com.mhd.vitalSignManagement.handler.exception.PatientAlreadyExistsException;
import com.mhd.vitalSignManagement.handler.exception.PatientDosentExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PatientAlreadyExistsException.class)
    public ResponseEntity<String> handle(PatientAlreadyExistsException exp){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exp.getMessage());
    }

    @ExceptionHandler(PatientDosentExistsException.class)
    public ResponseEntity<String> handle(PatientDosentExistsException exp){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exp.getMessage());
    }


    @ExceptionHandler(DeviceHasAlreadyBeenAddedToThisPatientException.class)
    public ResponseEntity<String> handle(DeviceHasAlreadyBeenAddedToThisPatientException exp){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exp.getMessage());
    }

}
