package org.example.deltawebfacade.kafka.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FileKafkaResource {
    private byte[] content;
    private String name;
    private String originalFilename;
    private String contentType;
}
