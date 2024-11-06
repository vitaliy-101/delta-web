package com.example.testkafka.file_system;

import com.example.testkafka.kafka.dtos.FileKafkaResource;
import lombok.*;

import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
public class ResourceData {
    private ResourceType resourceType;
    private String page;
    private String name;
    private String path;
    private String author;
    private Integer year;
    private Boolean isBase;
    private List<FileKafkaResource> fileKafkaResources;
}
