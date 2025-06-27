package com.mhd.DataCollection.exceptionHandler;

import com.mhd.DataCollection.Domain.model.Device;
import com.mhd.DataCollection.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FailedToGetReadingException.class)
    public ResponseEntity<String> handle(FailedToGetReadingException exp){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }

    @ExceptionHandler(ProtocolTypeNotAvailableException.class)
    public ResponseEntity<String> handle(ProtocolTypeNotAvailableException exp){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exp.getMessage());
    }

    @ExceptionHandler(DeviceAddFailedException.class)
    public ResponseEntity<String> handle(DeviceAddFailedException exp){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("false");
    }

    @ExceptionHandler(DeviceDeleteFailedException.class)
    public ResponseEntity<String> handle(DeviceDeleteFailedException exp){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<String> handle(DeviceNotFoundException exp){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());

    }

    @ExceptionHandler(FailedToParseVitalSignJsonException.class)
    public ResponseEntity<String> handle(FailedToParseVitalSignJsonException exp){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exp.getMessage());

    }

    @ExceptionHandler(FailedtoRefreshDeviceLimitCacheException.class)
    public ResponseEntity<String> handle(FailedtoRefreshDeviceLimitCacheException exp){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("false");
    }

    @ExceptionHandler(DeviceHasAlreadyBeenAddedException.class)
    public ResponseEntity<String> handle(DeviceHasAlreadyBeenAddedException exp){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
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
