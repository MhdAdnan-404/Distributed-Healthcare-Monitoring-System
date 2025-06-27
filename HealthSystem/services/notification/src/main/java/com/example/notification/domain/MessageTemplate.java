package com.example.notification.domain;

import com.example.notification.domain.Enums.NotificationType;
import com.example.notification.domain.Enums.PreferredLanguage;
import com.example.notification.domain.Enums.RecipientRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(collation = "notification_templates")
public class MessageTemplate {

    @Id
    private String id;

    @Indexed
    private NotificationType type;

    @Indexed
    private PreferredLanguage language;

    @Indexed
    private RecipientRole recipientRole;

    private String subject;

    private String body;


    public MessageTemplate(NotificationType type, PreferredLanguage language, RecipientRole role, String subject, String body) {
        this.body =body;
        this.recipientRole=role;
        this.type = type;
        this.language = language;
        this.subject = subject;
    }

}

