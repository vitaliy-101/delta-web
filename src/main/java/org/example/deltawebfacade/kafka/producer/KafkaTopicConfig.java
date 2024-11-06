package org.example.deltawebfacade.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic fileSaveTopic() {
        return TopicBuilder.name("file-save")
                .partitions(1)
                .build();
    }

    @Bean
    public NewTopic pathSaveTopic() {
        return TopicBuilder.name("path-save")
                .partitions(3)
                .build();
    }

    @Bean
    public NewTopic resourceDeleteTopic() {
        return TopicBuilder.name("resource-delete")
                .partitions(3)
                .build();
    }
}
