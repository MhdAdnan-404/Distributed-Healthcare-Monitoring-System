package com.mhd.AppointmentsManagement.controller;

import com.mhd.AppointmentsManagement.model.DTO.PrescriptionDTOS.CreatePrescriptionRequest;
import com.mhd.AppointmentsManagement.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/AddPrescriptionToAppointment/{id}")
    public ResponseEntity<Boolean> addPrescriptionToAppointment(@PathVariable Long id, @RequestBody @Valid CreatePrescriptionRequest request){
        return ResponseEntity.ok(appointmentService.addPrescriptionToAppointment(id,request));

    }


}
