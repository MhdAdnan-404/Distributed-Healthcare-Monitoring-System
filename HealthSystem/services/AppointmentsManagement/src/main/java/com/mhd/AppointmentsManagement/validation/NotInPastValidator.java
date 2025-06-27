package com.mhd.AppointmentsManagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class NotInPastValidator implements ConstraintValidator<NotInPast, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value != null && !value.isBefore(LocalDateTime.now());
    }
}
