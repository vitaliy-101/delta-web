package org.example.deltawebfacade.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.deltawebfacade.kafka.converter.DownloadResourceDeserializer;
import org.example.deltawebfacade.kafka.dtos.ResourceDownloadDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> resourceConsumerConfig() {
        var properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return properties;
    }

    @Bean
    public ConsumerFactory<String, String> resourcePageConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(resourceConsumerConfig(), new StringDeserializer(), new StringDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> resourcePageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(resourcePageConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ResourceDownloadDto> resourceDownloadConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(resourceConsumerConfig(), new StringDeserializer(), new DownloadResourceDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ResourceDownloadDto> resourceDownloadFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ResourceDownloadDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(resourceDownloadConsumerFactory());
        return factory;
    }
}
