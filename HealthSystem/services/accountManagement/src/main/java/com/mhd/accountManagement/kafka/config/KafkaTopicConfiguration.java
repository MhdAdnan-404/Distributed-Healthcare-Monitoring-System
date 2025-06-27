package com.mhd.accountManagement.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {

    @Bean
    public NewTopic AccountVerificationTopic(){
        return TopicBuilder
                .name("accountActivation-topic")
                .build();
    }

    @Bean
    public NewTopic ContactInfoUpdate(){
        return TopicBuilder
                .name("notificationUserAdd-topic")
                .build();
    }
}
