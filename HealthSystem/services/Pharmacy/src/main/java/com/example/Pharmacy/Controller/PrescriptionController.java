package com.example.Pharmacy.Controller;


import com.example.Pharmacy.Domain.DTO.PrescriptionFulFillmentDTO;
import com.example.Pharmacy.Domain.Prescription;
import com.example.Pharmacy.Services.PrescriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/getAll")
    public ResponseEntity<Page<Prescription>> getAllPrescriptions(Pageable pageable){
        return ResponseEntity.ok(prescriptionService.getAll(pageable));
    }


    @GetMapping("/getUnfulfilled")
    public ResponseEntity<Page<Prescription>> getUnfulfilledPrescriptions(Pageable pageable){
        return ResponseEntity.ok(prescriptionService.getUnfulfilledPrescriptions(pageable));
    }

    @PutMapping("/fulfillPrescription")
    public ResponseEntity<Boolean> fulFillPrescription(@RequestBody @Valid PrescriptionFulFillmentDTO request){
        return ResponseEntity.ok(prescriptionService.fulfill(request));

    }
}
