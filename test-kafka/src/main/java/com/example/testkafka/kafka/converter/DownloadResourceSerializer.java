package com.example.testkafka.kafka.converter;

import com.example.testkafka.kafka.dtos.DeleteResourceDto;
import com.example.testkafka.kafka.dtos.ResourceDownloadDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class DownloadResourceSerializer implements Serializer<ResourceDownloadDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, ResourceDownloadDto resourceDownloadDto) {
        try {
            return objectMapper.writeValueAsBytes(resourceDownloadDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize ResourceDownloadDto", e);
        }
    }
}
