package com.mhd.vitalSignConsumer.kafka.kafkaAlertProducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaVitalAlertTopicConfiguration {

    @Bean
    public NewTopic VitalAlertTopic(){
        return TopicBuilder
                .name("vitalAlert-topic")
                .build();
    }
}
