package com.mhd.AppointmentsManagement.model.DTO;

import com.mhd.AppointmentsManagement.model.Medication;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PrescriptionKafkaDTO(
        Long id,
        Integer patientId,
        Integer doctorId,
        LocalDateTime issuedDate,
        List<Medication> medicationList
) {
}
