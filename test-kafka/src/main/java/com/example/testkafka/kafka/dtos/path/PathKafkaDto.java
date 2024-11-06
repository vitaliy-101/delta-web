package com.example.testkafka.kafka.dtos.path;


import com.example.testkafka.kafka.dtos.file.FileKafkaDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
public class PathKafkaDto extends FileKafkaDto {
    private Boolean isBase;
}
