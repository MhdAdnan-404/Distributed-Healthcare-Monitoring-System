package com.example.notification.service;

import com.example.notification.clients.AccountManagementClient;
import com.example.notification.domain.Enums.ContactType;
import com.example.notification.domain.notificationContent.Contents.AppointmentCreationContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class feignClientService  {

    private final AccountManagementClient client;

    public AppointmentCreationContent buildContent(
            Integer doctorId, String doctorName,
            Integer patientId, String patientName,
            String appointmentType,
            Long duration, LocalDateTime startTime
    ) {
        Map<ContactType, String> doctorContacts = client.getContactsForEntity(doctorId);
        Map<ContactType, String> patientContacts = client.getContactsForEntity(patientId);

        return AppointmentCreationContent.builder()
                .doctorId(doctorId)
                .doctorName(doctorName)
                .patientId(patientId)
                .patientName(patientName)
                .startTime(startTime)
                .approxDurationInMinutes(duration)
                .type(appointmentType)
                .doctorContacts(doctorContacts)
                .patientContacts(patientContacts)
                .build();
    }


}
