package com.example.notification.service.Template;

import com.example.notification.domain.Enums.NotificationType;
import com.example.notification.domain.Enums.PreferredLanguage;
import com.example.notification.domain.Enums.RecipientRole;
import com.example.notification.domain.MessageTemplate;
import com.example.notification.exceptions.MessageTemplateNotFoundException;
import com.example.notification.repository.NotificationMessageTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TemplateService {

    @Autowired
    private NotificationMessageTemplateRepository repository;

    public String getProcessedBody(NotificationType type, PreferredLanguage language, RecipientRole role, Map<String, String> values) {
        MessageTemplate template = repository.findByTypeAndLanguageAndRecipientRole(type,language,role)
                .orElseThrow(()-> new MessageTemplateNotFoundException("Message template for this type or role dosen't exists"));

        String body = template.getBody();

        for (Map.Entry<String, String> entry : values.entrySet()) {
            body = body.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return body;
    }

    public String getSubject(NotificationType type, PreferredLanguage language, RecipientRole role){
        MessageTemplate template = repository.findByTypeAndLanguageAndRecipientRole(type,language,role)
                .orElseThrow(()-> new MessageTemplateNotFoundException("Message template for this type or role dosen't exists"));

        return template.getSubject();
    }
}
