package com.mhd.vitalSignConsumer.kafka.kafkaAlertProducer;


import com.mhd.vitalSignConsumer.model.Alert.VitalAlert;
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
public class VitalAlertProducer {

    private final KafkaTemplate<String, VitalAlert> vitalAlertKafkaTemplate;

    public void sendVitalAlertToKafka(VitalAlert vitalAlert){
        Message<VitalAlert> vitalAlertMessage = MessageBuilder
                .withPayload(vitalAlert)
                .setHeader(KafkaHeaders.TOPIC,"vitalAlert-topic")
                .build();
        vitalAlertKafkaTemplate.send(vitalAlertMessage);
    }
}
