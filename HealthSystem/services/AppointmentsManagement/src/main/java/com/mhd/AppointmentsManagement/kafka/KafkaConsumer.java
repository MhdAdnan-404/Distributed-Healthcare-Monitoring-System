package com.mhd.AppointmentsManagement.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mhd.AppointmentsManagement.model.DTO.PrescriptionDTOS.PrescriptionFulFillmentDTO;
import com.mhd.AppointmentsManagement.services.AppointmentService;
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
    AppointmentService appointmentService;


    @KafkaListener(topics = "PrescriptionFulfillment-topic")
    public void consumePrescriptionFulfillment(ConsumerRecord<String,String> record){
        String messageJson = record.value();

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            PrescriptionFulFillmentDTO prescriptionFulfillment = mapper.readValue(messageJson, PrescriptionFulFillmentDTO.class);
            System.out.println("Parsed ALERT: " + prescriptionFulfillment);

            appointmentService.FulfillPrescription(prescriptionFulfillment);

        } catch (Exception e) {
            log.error("Failed to parse Prescription message: {}", messageJson, e);
        }
    }
}
