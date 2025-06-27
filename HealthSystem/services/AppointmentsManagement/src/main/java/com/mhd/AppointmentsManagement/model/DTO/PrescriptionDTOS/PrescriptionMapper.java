package com.mhd.AppointmentsManagement.model.DTO.PrescriptionDTOS;

import com.mhd.AppointmentsManagement.model.DTO.PrescriptionKafkaDTO;
import com.mhd.AppointmentsManagement.model.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    public Prescription toPrescription(CreatePrescriptionRequest request) {
        return Prescription.builder()
                .patientId(request.patientId())
                .doctorId(request.doctorId())
                .issuedDate(request.issuedDate())
                .filled(false)
                .medicationList(request.medicationItemList())
                .build();
    }

    public PrescriptionKafkaDTO toPrescriptionKafkaDTO(Prescription prescription){
        return PrescriptionKafkaDTO
                .builder()
                .id(prescription.getId())
                .patientId(prescription.getPatientId())
                .doctorId(prescription.getDoctorId())
                .issuedDate(prescription.getIssuedDate())
                .medicationList(prescription.getMedicationList())
                .build();
    }
}
