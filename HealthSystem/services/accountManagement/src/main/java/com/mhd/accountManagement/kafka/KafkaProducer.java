package com.mhd.accountManagement.kafka;

import com.mhd.accountManagement.model.DTO.UserDTO.ContactInfoDTO;
import com.mhd.accountManagement.model.DTO.UserDTO.NotificationUser;
import com.mhd.accountManagement.model.DTO.UserDTO.VerificationNotification;
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
public class KafkaProducer {

    private final KafkaTemplate<String, VerificationNotification> verificationNotificationKafkaTemplate;
    private final KafkaTemplate<String, NotificationUser> notificationUserKafkaTemplate;

    public void sendToKafka_AccountVerification(VerificationNotification verificationNotification){
        Message<VerificationNotification> activationNotificationMessage = MessageBuilder
                .withPayload(verificationNotification)
                .setHeader(KafkaHeaders.TOPIC, "accountActivation-topic")
                .build();

        verificationNotificationKafkaTemplate.send(activationNotificationMessage);
    }

    public void sendToKafka_NotificationUserAdd(NotificationUser notificationUser){
        Message<NotificationUser> notificationUserMessage = MessageBuilder
                .withPayload(notificationUser)
                .setHeader(KafkaHeaders.TOPIC, "notificationUserAdd-topic")
                .build();

        notificationUserKafkaTemplate.send(notificationUserMessage);
    }

    public void sendToKafka_NotificationUserUpdate(NotificationUser notificationUser){
        Message<NotificationUser> notificationUserMessage = MessageBuilder
                .withPayload(notificationUser)
                .setHeader(KafkaHeaders.TOPIC, "notificationUserUpdate-topic")
                .build();

        notificationUserKafkaTemplate.send(notificationUserMessage);
    }



}
