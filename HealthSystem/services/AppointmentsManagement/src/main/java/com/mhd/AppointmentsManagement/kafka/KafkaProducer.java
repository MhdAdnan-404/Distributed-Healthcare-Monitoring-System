package com.mhd.AppointmentsManagement.kafka;

import com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS.AppointmentCancellation;
import com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS.AppointmentReminder;
import com.mhd.AppointmentsManagement.model.DTO.AppointmentDTOS.CreateAppointmentRequest;
import com.mhd.AppointmentsManagement.model.DTO.PrescriptionKafkaDTO;
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
    private final KafkaTemplate<String, CreateAppointmentRequest> appointmentCreationConfirmationTemplate;
    private final KafkaTemplate<String, AppointmentCancellation> appointmentCancellationKafkaTemplate;
    private final KafkaTemplate<String, AppointmentReminder> appointmentReminderKafkaTemplate;
    private final KafkaTemplate<String, PrescriptionKafkaDTO> prescriptionKafkaDTOKafkaTemplate;

    public void sendAppointmentCreationConfirmationToKafka(CreateAppointmentRequest appointmentCreationConfirmation){
        Message<CreateAppointmentRequest> appointmentCreationMessage = MessageBuilder
                .withPayload(appointmentCreationConfirmation)
                .setHeader(KafkaHeaders.TOPIC,"appointmentCreation-topic")
                .build();
        System.out.println("Sending to Kafka: " + appointmentCreationConfirmation);
        appointmentCreationConfirmationTemplate.send(appointmentCreationMessage);
    }

    public void sendAppointmentCancellationToKafka(AppointmentCancellation appointmentCancellation){
        Message<AppointmentCancellation> appointmentCancellationMessage = MessageBuilder
                .withPayload(appointmentCancellation)
                .setHeader(KafkaHeaders.TOPIC,"appointmentCancellation-topic")
                .build();
        System.out.println("Sending to Kafka: " + appointmentCancellation);
        appointmentCancellationKafkaTemplate.send(appointmentCancellationMessage);
    }

    public void sendAppointmentReminderToKafka(AppointmentReminder appointmentReminder){
        Message<AppointmentReminder> appointmentReminderMessage = MessageBuilder
                .withPayload(appointmentReminder)
                .setHeader(KafkaHeaders.TOPIC,"appointmentReminder-topic")
                .build();
        System.out.println("Sending to Kafka: " + appointmentReminder);
        appointmentReminderKafkaTemplate.send(appointmentReminderMessage);
    }

    public void sendAppointmentRescheduleToKafka(AppointmentReminder appointmentReschedule){
        Message<AppointmentReminder> appointmentRescheduleMessage = MessageBuilder
                .withPayload(appointmentReschedule)
                .setHeader(KafkaHeaders.TOPIC,"appointmentReschedule-topic")
                .build();
        System.out.println("Sending to Kafka: " + appointmentReschedule);
        appointmentReminderKafkaTemplate.send(appointmentRescheduleMessage);
    }


    public void sendPrescriptionToKafka(PrescriptionKafkaDTO prescriptionKafkaDTO){
        Message<PrescriptionKafkaDTO> perscriptionKafkaDTOMessage = MessageBuilder
                .withPayload(prescriptionKafkaDTO)
                .setHeader(KafkaHeaders.TOPIC,"Prescription-topic")
                .build();
        System.out.println("Sending to Kafka: " + prescriptionKafkaDTO);
        prescriptionKafkaDTOKafkaTemplate.send(perscriptionKafkaDTOMessage);
    }



}
