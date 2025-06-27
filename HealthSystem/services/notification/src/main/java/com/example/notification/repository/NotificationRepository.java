package com.example.notification.repository;

import com.example.notification.domain.Notification;
import com.example.notification.domain.notificationContent.InotificationContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
}
