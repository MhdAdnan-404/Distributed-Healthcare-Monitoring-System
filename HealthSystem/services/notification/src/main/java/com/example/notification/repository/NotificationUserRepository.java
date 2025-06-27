package com.example.notification.repository;

import com.example.notification.domain.NotificationUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationUserRepository extends MongoRepository<NotificationUser,String> {
    Optional<NotificationUser> findBySystemUserId(Integer systemUserId);

}
