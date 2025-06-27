package com.example.notification.repository;

import com.example.notification.domain.Enums.NotificationType;
import com.example.notification.domain.Enums.PreferredLanguage;
import com.example.notification.domain.Enums.RecipientRole;
import com.example.notification.domain.MessageTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationMessageTemplateRepository extends MongoRepository<MessageTemplate, String> {
    Optional<MessageTemplate> findByTypeAndLanguageAndRecipientRole(
            NotificationType type,
            PreferredLanguage language,
            RecipientRole role
    );
}
