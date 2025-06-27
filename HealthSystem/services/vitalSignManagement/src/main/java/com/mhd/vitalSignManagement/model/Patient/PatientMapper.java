package com.mhd.vitalSignManagement.model.Patient;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PatientMapper {

    public Patient toPatient(AddPatientRequest request){
        Patient patient = Patient.builder()
                .id(request.id())
                .name(request.name())
                .devicesId(new ArrayList<>())
                .build();
        return patient;
    }


}
