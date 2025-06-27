package com.example.Pharmacy.kafka;

import com.example.Pharmacy.Domain.DTO.PrescriptionKafkaDTO;
import com.example.Pharmacy.Services.PrescriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    @Autowired
    PrescriptionService prescriptionService;

    @KafkaListener(topics = "Prescription-topic")
    public void consumePrescriptions(ConsumerRecord<String,String> record){
        String messageJson = record.value();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            PrescriptionKafkaDTO prescriptionFulfillment = mapper.readValue(messageJson, PrescriptionKafkaDTO.class);
            System.out.println("Parsed ALERT: " + prescriptionFulfillment);

            prescriptionService.saveNewPrescription(prescriptionFulfillment);

        } catch (Exception e) {
            log.error("Failed to parse Prescription message: {}", messageJson, e);
        }
    }


}
