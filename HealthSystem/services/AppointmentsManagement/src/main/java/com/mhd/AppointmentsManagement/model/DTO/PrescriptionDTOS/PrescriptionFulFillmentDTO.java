package com.mhd.AppointmentsManagement.model.DTO.PrescriptionDTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PrescriptionFulFillmentDTO(

        Long prescriptionId,
        String filledByPharmacistName,

        Integer pharmacyId,

        LocalDateTime filledAt
) {
}
