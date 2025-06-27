package com.example.notification.domain.notificationContent;

import com.example.notification.service.NotificationService;
import com.netflix.spectator.impl.PatternExpr;
import org.springframework.beans.factory.annotation.Autowired;

public interface InotificationContent {

    public String toString();
    public void process(NotificationService notificationService);
}
