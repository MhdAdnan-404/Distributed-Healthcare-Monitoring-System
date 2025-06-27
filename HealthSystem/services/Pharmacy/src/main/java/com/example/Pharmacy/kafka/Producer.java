package com.example.Pharmacy.kafka;

import com.example.Pharmacy.Domain.DTO.PrescriptionFulFillmentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Producer {
    private final KafkaTemplate<String, PrescriptionFulFillmentDTO> prescriptionFulFillmentDTOKafkaTemplate;
    public void sendPrescriptionToKafka(PrescriptionFulFillmentDTO prescriptionFulFillmentDTO){
        Message<PrescriptionFulFillmentDTO> prescriptionFulFillmentDTOMessage = MessageBuilder
                .withPayload(prescriptionFulFillmentDTO)
                .setHeader(KafkaHeaders.TOPIC,"PrescriptionFulfillment-topic")
                .build();
        System.out.println("Sending to Kafka: " + prescriptionFulFillmentDTO);
        prescriptionFulFillmentDTOKafkaTemplate.send(prescriptionFulFillmentDTOMessage);
    }
}
