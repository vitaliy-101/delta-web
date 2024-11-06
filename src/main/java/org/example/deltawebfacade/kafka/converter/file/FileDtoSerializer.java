package org.example.deltawebfacade.kafka.converter.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.example.deltawebfacade.kafka.dtos.file.FileKafkaDto;

public class FileDtoSerializer implements Serializer<FileKafkaDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(String topic, FileKafkaDto fileKafkaDto) {
        try {
            return objectMapper.writeValueAsBytes(fileKafkaDto);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize FileDto", e);
        }
    }
}
