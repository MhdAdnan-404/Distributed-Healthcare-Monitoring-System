package com.example.Pharmacy.Services;

import com.example.Pharmacy.Domain.DTO.PrescriptionFulFillmentDTO;
import com.example.Pharmacy.Domain.DTO.PrescriptionKafkaDTO;
import com.example.Pharmacy.Domain.DTO.PrescriptionMapper;
import com.example.Pharmacy.Domain.Prescription;
import com.example.Pharmacy.Exception.PrescriptionNotFoundException;
import com.example.Pharmacy.Repository.PrescriptionRepo;
import com.example.Pharmacy.kafka.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service
public class PrescriptionService {

    @Autowired
    PrescriptionMapper prescriptionMapper;

    @Autowired
    PrescriptionRepo prescriptionRepo;

    @Autowired
    private Producer producer;

    public void saveNewPrescription(PrescriptionKafkaDTO prescriptionKafkaDTO) {
        Prescription prescription = prescriptionMapper.toPrescription(prescriptionKafkaDTO);
        prescriptionRepo.save(prescription);
    }

    public Page<Prescription> getAll(Pageable pageable) {
        return prescriptionRepo.findAll(pageable);
    }

    public Page<Prescription> getUnfulfilledPrescriptions(Pageable pageable) {
        return prescriptionRepo.findByFilledFalse(pageable);
    }

    public Boolean fulfill(PrescriptionFulFillmentDTO request) {
        Prescription prescription = prescriptionRepo.findById(request.prescriptionId())
                .orElseThrow(() -> new PrescriptionNotFoundException("Prescription not found"));

        prescription.setFilled(true);
        prescription.setFilledAt(LocalDateTime.now());
        prescription.setPharmacyId(request.pharmacyId());
        prescription.setFilledByPharmacistName(request.filledByPharmacistName());

        prescriptionRepo.save(prescription);


        producer.sendPrescriptionToKafka(request);

        return true;
    }
}
