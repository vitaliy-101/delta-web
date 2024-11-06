package com.example.testkafka.kafka.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ResourceDownloadDto {
    private Long id;
    private byte[] data;
    private String type;
}
