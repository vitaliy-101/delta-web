package com.example.testkafka.file_system.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private Long id;
    private String name;
    private String path;
    private String type;
    private String creationDate;
    private Integer year;
    private String author;
    private String downloadURL;

    public void setDownloadUrl() {
        this.downloadURL = "http://localhost:8081/delta/pages/download/" + this.id;
    }
}
