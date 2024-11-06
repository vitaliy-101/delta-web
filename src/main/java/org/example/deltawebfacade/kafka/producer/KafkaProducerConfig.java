package org.example.deltawebfacade.kafka.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.deltawebfacade.kafka.converter.DeleteResourceDtoSerializer;
import org.example.deltawebfacade.kafka.converter.file.FileDtoSerializer;
import org.example.deltawebfacade.kafka.converter.path.PathDtoSerializer;
import org.example.deltawebfacade.kafka.dtos.DeleteResourceDto;
import org.example.deltawebfacade.kafka.dtos.file.FileKafkaDto;
import org.example.deltawebfacade.kafka.dtos.path.PathKafkaDto;
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
    public ProducerFactory<String, FileKafkaDto> fileProducerFactory() {
        return new DefaultKafkaProducerFactory<>(resourceProducerConfig(), new StringSerializer(), new FileDtoSerializer());
    }

    @Bean
    public KafkaTemplate<String, FileKafkaDto> fileKafkaTemplate(
            ProducerFactory<String, FileKafkaDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, PathKafkaDto> pathProducerFactory() {
        return new DefaultKafkaProducerFactory<>(resourceProducerConfig(), new StringSerializer(), new PathDtoSerializer());
    }

    @Bean
    public KafkaTemplate<String, PathKafkaDto> pathKafkaTemplate(
            ProducerFactory<String, PathKafkaDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ProducerFactory<String, DeleteResourceDto> deleteResourceProducerFactory() {
        return new DefaultKafkaProducerFactory<>(resourceProducerConfig(), new StringSerializer(), new DeleteResourceDtoSerializer());
    }

    @Bean
    public KafkaTemplate<String, DeleteResourceDto> deleteResourceKafkaTemplate(
            ProducerFactory<String, DeleteResourceDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

}
