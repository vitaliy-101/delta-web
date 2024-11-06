package com.example.testkafka.kafka.converter;

import com.example.testkafka.kafka.dtos.DeleteResourceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

public class DeleteResourceDtoDeserializer implements Deserializer<DeleteResourceDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public DeleteResourceDto deserialize(String topic, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, DeleteResourceDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize DeleteResourceDto", e);
        }
    }
}