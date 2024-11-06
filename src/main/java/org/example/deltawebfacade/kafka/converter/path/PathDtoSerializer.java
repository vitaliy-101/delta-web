package org.example.deltawebfacade.kafka.converter.path;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.example.deltawebfacade.kafka.dtos.file.FileKafkaDto;
import org.example.deltawebfacade.kafka.dtos.path.PathKafkaDto;

public class PathDtoSerializer implements Serializer<PathKafkaDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, PathKafkaDto pathKafkaDto) {
        try {
            return objectMapper.writeValueAsBytes(pathKafkaDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize PathKafkaDto", e);
        }
    }
}

