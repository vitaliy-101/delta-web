package com.example.testkafka.kafka.converter.path;

import com.example.testkafka.kafka.dtos.file.FileKafkaDto;
import com.example.testkafka.kafka.dtos.path.PathKafkaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class PathDtoDeserializer implements Deserializer<PathKafkaDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public PathKafkaDto deserialize(String topic, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, PathKafkaDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize PathKafkaDto", e);
        }
    }
}
