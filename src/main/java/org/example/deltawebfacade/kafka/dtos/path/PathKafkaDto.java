package org.example.deltawebfacade.kafka.dtos.path;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.kafka.dtos.file.FileKafkaDto;

@Getter
@Setter
@RequiredArgsConstructor
public class PathKafkaDto extends FileKafkaDto {
    private Boolean isBase;
}
