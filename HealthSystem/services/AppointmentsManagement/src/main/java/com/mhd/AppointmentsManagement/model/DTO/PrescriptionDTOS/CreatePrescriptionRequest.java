package com.mhd.AppointmentsManagement.model.DTO.PrescriptionDTOS;

import com.mhd.AppointmentsManagement.model.Medication;
import com.mhd.AppointmentsManagement.validation.NotInPast;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreatePrescriptionRequest(

        @NotNull(message = "PatientId Can't be null")
        Integer patientId,

        @NotNull(message = "doctorId Can't be null")
        Integer doctorId,

        @NotInPast(message = "issuedDate can't be in the past")
        @NotNull
        LocalDateTime issuedDate,
        @NotNull(message = "Medication list can't be null")
        List<Medication> medicationItemList
) {
}
