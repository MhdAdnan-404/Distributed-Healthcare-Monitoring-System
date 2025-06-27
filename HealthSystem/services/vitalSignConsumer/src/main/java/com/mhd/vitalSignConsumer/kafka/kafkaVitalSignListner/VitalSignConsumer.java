package com.mhd.vitalSignConsumer.kafka.kafkaVitalSignListner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mhd.vitalSignConsumer.model.VitalSign.vitalSignMeasurment.VitalSignMeasurment;
import com.mhd.vitalSignConsumer.service.LimitsService;
import com.mhd.vitalSignConsumer.service.VitalSignConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class VitalSignConsumer {


    @Autowired
    private VitalSignConsumerService vitalSignConsumerService;

    @Autowired
    private LimitsService limitsService;

    @KafkaListener(topics = "vitalSign-topic",groupId = "vital-sign-consumer")
    public void consumeVitalSign(ConsumerRecord<String, String> record) {
        String messageJson = record.value();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            VitalSignMeasurment vitalSign = mapper.readValue(messageJson, VitalSignMeasurment.class);
            System.out.println("Parsed: " + vitalSign);

            limitsService.processMeasurment(vitalSign);
            vitalSignConsumerService.saveVitalSignDoc(vitalSign);

        } catch (Exception e) {
            System.err.println(" Failed to parse message: " + messageJson);
            e.printStackTrace();
        }
    }

}
