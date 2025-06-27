package com.example.notification.domain.notificationContent.Contents;

import com.example.notification.domain.notificationContent.InotificationContent;
import com.example.notification.service.NotificationService;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountVerificationContent implements InotificationContent {

    private boolean success;
    private String username;
    private String email;
    private Instant timestamp;

    @Override
    public String toString() {
        return "Dear " + username + ", your account (" + email + ") was successfully verified on " + timestamp + ".";
    }

    @Override
    public void process(NotificationService notificationService) {
        notificationService.processAccountVerification(this);
    }
}
