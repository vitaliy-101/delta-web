package com.example.testkafka.kafka.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FileKafkaResource {
    private byte[] content;
    private String name;
    private String originalFilename;
    private String contentType;
}
