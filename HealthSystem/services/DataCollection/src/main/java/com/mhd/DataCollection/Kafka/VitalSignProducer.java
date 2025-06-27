package com.mhd.DataCollection.Kafka;
import com.mhd.DataCollection.Domain.model.Measurment.VitalSignMeasurment;
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
public class VitalSignProducer {
    private final KafkaTemplate<String, VitalSignMeasurment> vitalkafkaTemplate;

    public void sendVitalSignToKafka(VitalSignMeasurment vitalSignMeasurment){
        Message<VitalSignMeasurment> vitalSignResponseMessage = MessageBuilder
                .withPayload(vitalSignMeasurment)
                .setHeader(KafkaHeaders.TOPIC, "vitalSign-topic")
                .build();
        System.out.println("Sending to Kafka: " + vitalSignResponseMessage);
        vitalkafkaTemplate.send(vitalSignResponseMessage);
    }
}
