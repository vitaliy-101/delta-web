package com.example.testkafka.file_system;

import com.example.testkafka.model.FileData;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class MinioResource {
    private String name;
    private String path;
    private boolean isBase;
    private byte[] data;
}
