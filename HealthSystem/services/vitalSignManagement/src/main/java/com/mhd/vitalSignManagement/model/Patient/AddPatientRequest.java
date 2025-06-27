package com.mhd.vitalSignManagement.model.Patient;

import jakarta.validation.constraints.NotNull;

public record AddPatientRequest(

        @NotNull(message = "patientId Can't be null")
        Integer id,
        String name
) {
}
