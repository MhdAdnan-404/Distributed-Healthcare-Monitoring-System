package com.example.Pharmacy.Domain.DTO;

import com.example.Pharmacy.Domain.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {
    public Prescription toPrescription(PrescriptionKafkaDTO prescriptionKafkaDTO){
        return Prescription
                .builder()
                .prescriptionId(prescriptionKafkaDTO.id())
                .patientId(prescriptionKafkaDTO.patientId())
                .doctorId(prescriptionKafkaDTO.doctorId())
                .issuedDate(prescriptionKafkaDTO.issuedDate())
                .medicationList(prescriptionKafkaDTO.medicationList())
                .filled(false)
                .build();
    }
}
