package com.example.testkafka.file_system.generator;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PathResponse {
    private Long id;
    private String path;
    private String type;
    private String creationDate;
    private Integer countFiles;
    private Integer year;
    private String author;
    private List<FileResponse> files = new ArrayList<>();
    private List<PathResponse> paths = new ArrayList<>();

    public void addFile(FileResponse file) {
        if (files == null) {
            files = new ArrayList<>();
        }
        files.add(file);
    }

    public void addPath(PathResponse path) {
        if (paths == null) {
            paths = new ArrayList<>();
        }
        paths.add(path);
    }

}