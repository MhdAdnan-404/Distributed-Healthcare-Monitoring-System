package com.example.notification.domain.notificationContent.Contents;

import com.example.notification.domain.Enums.ContactType;
import com.example.notification.domain.notificationContent.InotificationContent;
import com.example.notification.service.NotificationService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Builder
@Getter
@Setter
public class AppointmentRescheduleContent implements InotificationContent {

    private Long appointmentId;
    private Integer doctorId;
    private Integer patientId;
    private String doctorName;
    private String patientName;
    private LocalDateTime appointmentStartTime;
    private Map<ContactType, String> doctorContacts;
    private Map<ContactType, String> patientContacts;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format(
                "Appointment ID %d has been rescheduled between Dr. %s and patient %s. New time: %s",
                appointmentId,
                doctorName,
                patientName,
                appointmentStartTime.format(formatter)
        );
    }

    @Override
    public void process(NotificationService notificationService) {
        notificationService.processAppointmentReschedule(this);
    }
}
