package org.example.deltawebfacade.kafka.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.example.deltawebfacade.kafka.dtos.ResourceDownloadDto;

public class DownloadResourceDeserializer implements Deserializer<ResourceDownloadDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ResourceDownloadDto deserialize(String topic, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, ResourceDownloadDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize ResourceDownloadDto", e);
        }
    }
}