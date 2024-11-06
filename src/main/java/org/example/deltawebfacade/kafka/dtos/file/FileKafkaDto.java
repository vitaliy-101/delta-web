package org.example.deltawebfacade.kafka.dtos.file;

import lombok.*;
import org.example.deltawebfacade.kafka.dtos.FileKafkaResource;

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
