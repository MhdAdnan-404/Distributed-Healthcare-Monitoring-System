package com.mhd.AppointmentsManagement.controller;

import com.mhd.AppointmentsManagement.model.Appointment;
import com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS.CreateAppointmentRequest;
import com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS.RescheduleAppointmentRequest;
import com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS.updateAppointmentStatusRequest;
import com.mhd.AppointmentsManagement.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/createAppointment")
    public ResponseEntity<Boolean>createAppointment(@RequestBody @Valid CreateAppointmentRequest createAppointmentRequest){
        return ResponseEntity.ok(appointmentService.createAppointment(createAppointmentRequest));

    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Appointment>> getAllAppointments(Pageable pageable){
        return ResponseEntity.ok(appointmentService.getAll(pageable));

    }

    @GetMapping("/getAppointmentsForPatient/{id}")
    public ResponseEntity<Page<Appointment>> getAllAppointmentsForPatient(Pageable pageable, @PathVariable Integer id){
        return ResponseEntity.ok(appointmentService.getAppointmentsForPatient(pageable, id));
    }

    @GetMapping("/getAppointmentsForDoctor/{id}")
    public ResponseEntity<Page<Appointment>> getAllAppointmentsForDoctor(Pageable pageable, @PathVariable Integer id){
        return ResponseEntity.ok(appointmentService.getAppointmentsForDoctor(pageable, id));
    }

    @PutMapping("/deleteAppointment/{id}")
    public ResponseEntity<Boolean> deleteAppointment(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.deleteAppointment(id));
    }

    @PutMapping("/changeAppointmentStatue")
    public ResponseEntity<Boolean> updateStatus(@RequestBody @Valid updateAppointmentStatusRequest request){
        return ResponseEntity.ok(appointmentService.updateAppointmentStatus(request));

    }

    @PutMapping("/RescheduleAppointment")
    public ResponseEntity<Boolean> rescheduleAppointment(@RequestBody @Valid RescheduleAppointmentRequest request){
        return ResponseEntity.ok(appointmentService.rescheduleAppointment(request));

    }
}
