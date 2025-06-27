package com.mhd.accountManagement.handler;

import com.mhd.accountManagement.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserDosentExistException.class)
    public ResponseEntity<String> handle(UserDosentExistException exp){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exp.getMessage());
    }

    @ExceptionHandler(UserHasNotBeenActivatedException.class)
    public ResponseEntity<String> handle(UserHasNotBeenActivatedException exp){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(exp.getMessage());
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> handle(UserExistsException exp){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exp.getMessage());
    }

    @ExceptionHandler(UserRegisterException.class)
    public ResponseEntity<String> handle(UserRegisterException exp){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exp.getMessage());
    }
    @ExceptionHandler(InvalidActivationTokenException.class)
    public ResponseEntity<String> handleInvalidToken(InvalidActivationTokenException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }

    @ExceptionHandler(AccountAlreadyActivatedException.class)
    public ResponseEntity<String> handleAlreadyActivated(AccountAlreadyActivatedException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPassword(InvalidPasswordException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exp.getMessage());
    }

    @ExceptionHandler(NoPendingDoctorException.class)
    public ResponseEntity<String> handleNoPendingDoctors(NoPendingDoctorException exp) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exp.getMessage());
    }

    @ExceptionHandler(ActivationFailedException.class)
    public ResponseEntity<String> handle(ActivationFailedException exp){
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
