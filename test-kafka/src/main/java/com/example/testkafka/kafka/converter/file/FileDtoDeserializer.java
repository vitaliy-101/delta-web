package com.example.testkafka.kafka.converter.file;

import com.example.testkafka.kafka.dtos.file.FileKafkaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class FileDtoDeserializer implements Deserializer<FileKafkaDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public FileKafkaDto deserialize(String topic, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, FileKafkaDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize FileDto", e);
        }
    }
}
