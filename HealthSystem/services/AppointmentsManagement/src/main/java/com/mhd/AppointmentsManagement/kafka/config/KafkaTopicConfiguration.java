package com.mhd.AppointmentsManagement.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic AppointmentReminderTopic(){
        return TopicBuilder
                .name("appointmentReminder-topic")
                .build();
    }

    @Bean
    public NewTopic AppointmentUpdateTopic(){
        return TopicBuilder
                .name("appointmentUpdate-topic")
                .build();
    }

    @Bean
    public NewTopic AppointmentCancellationTopic(){
        return TopicBuilder
                .name("appointmentCancellation-topic")
                .build();
    }

    @Bean
    public NewTopic AppointmentMeetingAlertTopic(){
        return TopicBuilder
                .name("appointmentMeetingAlert-topic")
                .build();
    }

    @Bean
    public NewTopic AppointmentCreationConfirmation(){
        return TopicBuilder
                .name(".")
                .build();
    }

    @Bean
    public NewTopic AppointmentReschedule(){
        return TopicBuilder
                .name("appointmentReschedule-topic")
                .build();
    }

    @Bean
    public NewTopic Prescription(){
        return TopicBuilder
                .name("Prescription-topic")
                .build();
    }
}
