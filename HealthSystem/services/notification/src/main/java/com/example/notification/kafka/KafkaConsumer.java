package com.example.notification.kafka;

import com.example.notification.domain.NotificationUser;
import com.example.notification.domain.notificationContent.Contents.AccountVerificationContent;
import com.example.notification.domain.notificationContent.InotificationContent;
import com.example.notification.domain.notificationContent.NotificationContentFactory;
import com.example.notification.kafka.Alert.VitalAlert;
import com.example.notification.service.NotificationService;
import com.example.notification.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationContentFactory notificationContentFactory;

    @Autowired
    UserService userService;

    @KafkaListener(topics="vitalAlert-topic")
    public void consumeVitalAlertMessage(ConsumerRecord<String,String> record){
        String messageJson = record.value();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            VitalAlert vitalAlert = mapper.readValue(messageJson, VitalAlert.class);
            System.out.println("Parsed ALERT: " + vitalAlert);

            notificationService.processVitalAlertMessage(vitalAlert);

        } catch (Exception e) {
            log.error("Failed to parse VitalAlert message: {}", messageJson, e);
        }
    }

    @KafkaListener(topics = "accountActivation-topic")
    public void consumeAccountActivationMessage(ConsumerRecord<String, String> record) {
        String messageJson = record.value();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            Map<String, Object> payload = mapper.readValue(messageJson, Map.class);
            InotificationContent content = notificationContentFactory.create(record.topic(), payload);
            content.process(notificationService);

        } catch (Exception e) {
            log.error("Failed to parse account verification message: {}", messageJson, e);
        }
    }



    @KafkaListener(topics = {"appointmentCancellation-topic","appointmentReminder-topic","appointmentCreation-topic","appointmentReschedule-topic"})
    public void consumeAppointmentMessage(ConsumerRecord<String, String> record) {
        String messageJson = record.value();
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            Map<String, Object> payload = mapper.readValue(messageJson, Map.class);
            InotificationContent content = notificationContentFactory.create(record.topic(), payload);
            content.process(notificationService);

        } catch (Exception e) {
            log.error("Failed to parse appointment message from topic {}: {}", record.topic(), messageJson, e);
        }
    }



    @KafkaListener(topics = {"notificationUserAdd-topic","notificationUserUpdate-topic"})
    public void consumeNotificationUserAdd(ConsumerRecord<String, String> record) {
        String messageJson = record.value();
        String topic = record.topic();
        try {
            ObjectMapper mapper = new ObjectMapper();

            NotificationUser user = mapper.readValue(messageJson, NotificationUser.class);

            switch (topic) {
                case "notificationUserAdd-topic" -> userService.saveUser(user);
                case "notificationUserUpdate-topic" -> userService.updateUser(user);
                default -> log.warn("Unhandled topic: {}", topic);
            }

        } catch (Exception e) {
            log.error("Failed to parse account verification message: {}", messageJson, e);
        }
    }
}
