package com.example.Pharmacy.Domain.DTO;

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
