package com.example.notification.domain.notificationContent.Contents;

import com.example.notification.domain.Enums.ContactType;
import com.example.notification.domain.notificationContent.InotificationContent;
import com.example.notification.service.NotificationService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Builder
@Getter
@Setter
public class AppointmentCancelledContent implements InotificationContent {
    private Long appointmentId;
    private Integer patientId;
    private Integer doctorId;
    private String patientName;
    private String doctorName;
    private String appointmentStatues;
    private Map<ContactType, String> doctorContacts;
    private Map<ContactType, String> patientContacts;



    @Override
    public String toString() {
        return String.format(
                "Appointment ID %d has been cancelled. Current status: %s.",
                appointmentId,
                appointmentStatues
        );
    }

    @Override
    public void process(NotificationService notificationService) {
        notificationService.processAppointmentCancellation(this);
    }
}
