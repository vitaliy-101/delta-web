package org.example.deltawebfacade.kafka.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ResourceDownloadDto implements Serializable {
    private Long id;
    private byte[] data;
    private String type;
}
