package com.example.testkafka.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic resourcePageTopic() {
        return TopicBuilder.name("page-resources")
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic downloadResourceTopic() {
        return TopicBuilder.name("download-resource")
                .partitions(3)
                .build();
    }
}
