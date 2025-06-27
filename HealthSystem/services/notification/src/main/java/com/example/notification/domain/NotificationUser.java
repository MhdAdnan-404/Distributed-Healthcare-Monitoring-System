package com.example.notification.domain;

import com.example.notification.domain.Enums.ContactType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class NotificationUser {
    @Id
    private String id;

    @Indexed(unique = true)
    private Integer systemUserId;

    private String preferredLanguage;

    private Map<ContactType, String> contacts = new HashMap<>();
}
