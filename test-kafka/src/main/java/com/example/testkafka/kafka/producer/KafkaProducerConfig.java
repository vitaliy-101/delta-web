package com.example.testkafka.kafka.producer;

import com.example.testkafka.kafka.converter.DownloadResourceSerializer;
import com.example.testkafka.kafka.dtos.ResourceDownloadDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String, Object> resourceProducerConfig() {
        var properties = new HashMap<String, Object>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "22000000");
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "22000000");
        return properties;
    }

    @Bean
    public ProducerFactory<String, String> resourcePageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(resourceProducerConfig(), new StringSerializer(), new StringSerializer());
    }

    @Bean
    public KafkaTemplate<String, String> resourcePageTemplate(
            ProducerFactory<String, String> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, ResourceDownloadDto> downloadResourceProducerFactory() {
        return new DefaultKafkaProducerFactory<>(resourceProducerConfig(), new StringSerializer(), new DownloadResourceSerializer());
    }

    @Bean
    public KafkaTemplate<String, ResourceDownloadDto> downloadResourceTemplate(
            ProducerFactory<String, ResourceDownloadDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}