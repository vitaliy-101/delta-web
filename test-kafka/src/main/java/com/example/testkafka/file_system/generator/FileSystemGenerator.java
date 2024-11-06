package com.example.testkafka.file_system.generator;

import com.example.testkafka.mapper.FileEntityMapper;
import com.example.testkafka.model.FileData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Getter
public class FileSystemGenerator {
    private final FileEntityMapper fileEntityMapper;

    private PathResponse createBasePathResponse(List<FileData> files, String page) {
        return PathResponse.builder()
                .path(page)
                .type("path")
                .countFiles(files.size()).build();
    }

    public PathResponse generate(String page, List<FileData> files) {
        return convertFilesToPathStructure(fileEntityMapper.convertFromModelListToResponse(files),
                createBasePathResponse(files, page));
    }

    public PathResponse convertFilesToPathStructure(List<FileResponse> files, PathResponse path) {
        for (var file : files) {
            var pathName = path.getPath();
            if (!file.getType().equals("path") && file.getPath().equals(pathName)) {
                addNewFile(path, file);
                continue;
            }
            if (file.getType().equals("path") && file.getPath().startsWith(pathName) && file.getPath().split("/").length == pathName.split("/").length + 1) {
                var currentFiles = getFilterFilesByPathName(files, file);
                path.addPath(convertFilesToPathStructure(currentFiles, fileEntityMapper.convertFromFileResponseToPathResponse(file, currentFiles)));
            }
        }
        return path;
    }

    private void addNewFile(PathResponse pathResponse, FileResponse fileResponse) {
        fileResponse.setDownloadUrl();
        pathResponse.addFile(fileResponse);
    }

    private List<FileResponse> getFilterFilesByPathName(List<FileResponse> files, FileResponse file) {
        return files.stream()
                .filter(currentFile -> currentFile.getPath().startsWith(file.getPath()))
                .toList();
    }
}

