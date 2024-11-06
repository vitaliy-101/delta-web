package com.example.testkafka.kafka.dtos.file;

import com.example.testkafka.kafka.dtos.FileKafkaResource;
import lombok.*;


import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class FileKafkaDto {
    private String page;
    private String path;
    private String author;
    private Integer year;
    private List<FileKafkaResource> fileKafkaResources;
}