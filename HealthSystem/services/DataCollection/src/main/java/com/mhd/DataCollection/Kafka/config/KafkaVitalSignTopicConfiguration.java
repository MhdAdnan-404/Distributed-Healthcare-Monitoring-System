package com.mhd.DataCollection.Kafka.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaVitalSignTopicConfiguration {

    @Bean
    public NewTopic vitalSignTopic(){
        return TopicBuilder
                .name("vitalSign-topic")
                .build();
    }
}
