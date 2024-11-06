package com.example.testkafka.kafka.consumer;


import com.example.testkafka.kafka.converter.DeleteResourceDtoDeserializer;
import com.example.testkafka.kafka.converter.file.FileDtoDeserializer;
import com.example.testkafka.kafka.converter.path.PathDtoDeserializer;
import com.example.testkafka.kafka.dtos.DeleteResourceDto;
import com.example.testkafka.kafka.dtos.file.FileKafkaDto;
import com.example.testkafka.kafka.dtos.path.PathKafkaDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;


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
    public ConsumerFactory<String, FileKafkaDto> fileConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(resourceConsumerConfig(), new StringDeserializer(), new FileDtoDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FileKafkaDto> fileFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FileKafkaDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(fileConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PathKafkaDto> pathConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(resourceConsumerConfig(), new StringDeserializer(), new PathDtoDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PathKafkaDto> pathFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PathKafkaDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pathConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, DeleteResourceDto> deleteResourceConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(resourceConsumerConfig(), new StringDeserializer(), new DeleteResourceDtoDeserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeleteResourceDto> deleteResourceFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DeleteResourceDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deleteResourceConsumerFactory());
        return factory;
    }


}
