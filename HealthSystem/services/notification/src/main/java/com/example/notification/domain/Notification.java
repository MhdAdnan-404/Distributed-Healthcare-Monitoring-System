package com.example.notification.domain;

import com.example.notification.domain.Enums.NotificationSeverity;
import com.example.notification.domain.Enums.NotificationType;
import com.example.notification.domain.notificationContent.InotificationContent;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {
    @Id
    private String id;

    private NotificationType type;
    private LocalDateTime notificationDate;
    private NotificationSeverity notificationSeverity;
    private InotificationContent notificationContent;

}
