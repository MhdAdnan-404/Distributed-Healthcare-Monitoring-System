package com.example.notification.domain.notificationContent;

import com.example.notification.clients.AccountManagementClient;
import com.example.notification.domain.notificationContent.Contents.*;
import com.example.notification.exceptions.TopicNotFoundException;
import com.example.notification.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationContentFactory {

    @Autowired
    private final AccountManagementClient client;

    @Autowired
    private final UserService userService;

    private final ObjectMapper mapper;


    public InotificationContent create(String topic, Map<String, Object> payload){
        return switch (topic){
            case "accountActivation-topic" -> {
                yield mapper.convertValue(payload,AccountVerificationContent.class);
            }
            case "appointmentCancellation-topic" -> {
                AppointmentCancelledContent appointmentCancelledContent = mapper.convertValue(payload,AppointmentCancelledContent.class);

                appointmentCancelledContent.setPatientContacts(userService.getUser(appointmentCancelledContent.getPatientId()).getContacts());
                appointmentCancelledContent.setDoctorContacts(userService.getUser(appointmentCancelledContent.getDoctorId()).getContacts());

                yield appointmentCancelledContent;
            }

            case "appointmentCreation-topic" -> {
                AppointmentCreationContent appointmentCreationContent = mapper.convertValue(payload,AppointmentCreationContent.class);

                appointmentCreationContent.setPatientContacts(userService.getUser(appointmentCreationContent.getPatientId()).getContacts());
                appointmentCreationContent.setDoctorContacts(userService.getUser(appointmentCreationContent.getDoctorId()).getContacts());

                yield appointmentCreationContent;
            }

            case "appointmentReminder-topic" -> {
                AppointmentReminderContent appointmentReminderContent = mapper.convertValue(payload, AppointmentReminderContent.class);

                appointmentReminderContent.setPatientContacts(userService.getUser(appointmentReminderContent.getPatientId()).getContacts());
                appointmentReminderContent.setDoctorContacts(userService.getUser(appointmentReminderContent.getDoctorId()).getContacts());

                yield appointmentReminderContent;
            }
            case "appointmentReschedule-topic" -> {
                AppointmentRescheduleContent appointmentRescheduleContent = mapper.convertValue(payload,AppointmentRescheduleContent.class);

                appointmentRescheduleContent.setPatientContacts(userService.getUser(appointmentRescheduleContent.getPatientId()).getContacts());
                appointmentRescheduleContent.setDoctorContacts(userService.getUser(appointmentRescheduleContent.getDoctorId()).getContacts());

                yield appointmentRescheduleContent;
            }

            default -> throw new TopicNotFoundException("Unexpected value: " + topic);
        };
    }
}
