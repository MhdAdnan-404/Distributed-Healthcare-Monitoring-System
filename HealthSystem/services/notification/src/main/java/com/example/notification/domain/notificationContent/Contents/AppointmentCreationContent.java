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
public class AppointmentCreationContent implements InotificationContent {
    private Integer doctorId;
    private String doctorName;
    private Integer patientId;
    private String patientName;
    private LocalDateTime startTime;
    private Long approxDurationInMinutes;
    private String type;
    private Map<ContactType, String> doctorContacts;
    private Map<ContactType, String> patientContacts;


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format(
                "A new appointment of type '%s' has been scheduled between Dr. %s and patient %s on %s. Estimated duration: %d minutes.",
                type,
                doctorName,
                patientName,
                startTime.format(formatter),
                approxDurationInMinutes
        );
    }
    @Override
    public void process(NotificationService notificationService) {
        notificationService.processAppointmentCreation(this);
    }
}
