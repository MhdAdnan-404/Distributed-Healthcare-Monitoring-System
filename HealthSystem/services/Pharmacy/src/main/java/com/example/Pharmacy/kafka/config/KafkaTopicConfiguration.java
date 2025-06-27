package com.example.Pharmacy.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic Prescription(){
        return TopicBuilder
                .name("PrescriptionFulfillment-topic")
                .build();
    }
}
