package com.mhd.accountManagement.Clients.VitalSignManagementMS;


import com.mhd.accountManagement.model.DTO.PatientDTO.AddPatientToVitalManagementRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name="vital-SignManagement-service",
        url="${application.config.vitalSignManagement-url}"
)
public interface VitalSignManagementClient {

    @PostMapping("/patient/addPatient")
    Boolean addPatient(@Valid @RequestBody AddPatientToVitalManagementRequest request);

}
