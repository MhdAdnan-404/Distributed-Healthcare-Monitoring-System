package com.example.Pharmacy.Domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PrescriptionFulFillmentDTO(

        @NotNull(message = "Prescription ID cannot be null")
        Long prescriptionId,
        @NotBlank(message = "Pharmacist name is required")
        String filledByPharmacistName,

        @NotNull(message = "Pharmacy ID cannot be null")
        @Positive(message = "Pharmacy ID must be a positive number")
        Integer pharmacyId,

        @NotNull(message = "Filled date and time is required")
        @PastOrPresent(message = "Filled date cannot be in the future")
        LocalDateTime filledAt
) {
}
