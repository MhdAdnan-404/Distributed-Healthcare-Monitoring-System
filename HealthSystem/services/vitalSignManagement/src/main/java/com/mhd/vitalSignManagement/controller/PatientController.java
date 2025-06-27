package com.mhd.vitalSignManagement.controller;

import com.mhd.vitalSignManagement.model.Patient.AddPatientRequest;
import com.mhd.vitalSignManagement.model.VitalSignDocument;
import com.mhd.vitalSignManagement.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService service;

    @PostMapping("/addPatient")
    public ResponseEntity<Boolean> addPatient(@RequestBody @Valid AddPatientRequest request){
        return ResponseEntity.ok(service.addPatient(request));
    }

    @DeleteMapping("/deletePatient/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable Integer id){
        return ResponseEntity.ok(service.deletePatient(id));
    }

    @GetMapping("/gettingCurrentReadings/{id}")
    public ResponseEntity<List<VitalSignDocument>> getCurrReadings(@PathVariable Integer id){
        return ResponseEntity.ok(service.getTheCurrReadgins(id));
    }



}
