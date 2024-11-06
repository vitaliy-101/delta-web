package org.example.deltawebfacade.kafka.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.example.deltawebfacade.kafka.dtos.DeleteResourceDto;

public class DeleteResourceDtoSerializer implements Serializer<DeleteResourceDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, DeleteResourceDto deleteResourceDto) {
        try {
            return objectMapper.writeValueAsBytes(deleteResourceDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize DeleteResourceDto", e);
        }
    }
}
