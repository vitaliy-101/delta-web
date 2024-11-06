package com.example.testkafka.file_system.strategy;

import com.example.testkafka.file_system.MinioResource;
import com.example.testkafka.file_system.ResourceData;
import com.example.testkafka.file_system.ResourceType;
import com.example.testkafka.file_system.generator.PathResponse;
import com.example.testkafka.kafka.dtos.FileKafkaResource;
import com.example.testkafka.kafka.dtos.ResourceDownloadDto;
import com.example.testkafka.mapper.FileEntityMapper;
import com.example.testkafka.minio.MinioService;
import com.example.testkafka.model.FileData;
import com.example.testkafka.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public abstract class ResourceService {
    protected final FileEntityMapper fileEntityMapper;
    protected final FileRepository fileRepository;
    protected final MinioService minioService;
    protected static final String PATH_TYPE = "path";

    private void setFileNameByDuplicates(FileData fileData) {
        var fileNumber = 1;
        var startName = fileData.getName();
        while (fileRepository.selectExistFile(fileData.getPath(), fileData.getName()) != null) {
            fileData.setName(startName + "(" + fileNumber + ")");
            fileNumber += 1;
        }
    }

    protected FileData saveFileEntityToStore(FileData fileData, FileKafkaResource fileResource) {
        var fileDataToStore = FileData.builder()
                .year(fileData.getYear())
                .author(fileData.getAuthor())
                .creationDate(fileData.getCreationDate())
                .path(fileData.getPath())
                .isBase(fileData.isBase())
                .name(fileResource.getOriginalFilename())
                .type(fileResource.getContentType())
                .build();
        setFileNameByDuplicates(fileDataToStore);
        return fileRepository.save(fileDataToStore);
    }

    protected MinioResource saveFileToStoreAndCreateMinioResource(FileData fileData, FileKafkaResource fileResource) {
        var savedFileData = saveFileEntityToStore(fileData, fileResource);
        var minioResource = fileEntityMapper.convertFromFileDataToMinioResource(savedFileData);
        minioResource.setData(fileResource.getContent());
        return minioResource;

    }

    protected void createNecessaryPaths(FileData fileData) {
        var path = fileData.getPath();
        var splitPath = Arrays.stream(path.split("/")).toList();
        var pathName = splitPath.getFirst();
        for (var addName : splitPath.subList(1, splitPath.size())) {
            pathName = getNextPathName(pathName, addName, fileData);
        }
        getNextPathName(pathName, "", fileData);
    }

    private String getNextPathName(String pathName, String addName, FileData fileData) {
        if (!fileRepository.existPathByPathStart(pathName)) {
            createNecessaryPath(fileData, pathName);
        }
        pathName += "/" + addName;
        return pathName;
    }

    protected void createNecessaryPath(FileData fileData, String pathName) {
        var fileDataToStore = FileData.builder()
                .year(fileData.getYear())
                .author(fileData.getAuthor())
                .creationDate(fileData.getCreationDate())
                .isBase(fileData.isBase())
                .path(pathName)
                .type(PATH_TYPE)
                .build();
        fileRepository.save(fileDataToStore);
    }

    protected List<MinioResource> saveFilesToStoreAndCreateMinioFiles(ResourceData resourceData) {
        var fileData = fileEntityMapper.convertFromResourceData(resourceData);
        createNecessaryPaths(fileData);
        return resourceData.getFileKafkaResources().stream()
                .map(fileResource -> saveFileToStoreAndCreateMinioResource(fileData, fileResource))
                .toList();
    }

    protected FileData findResourceById(Long id) throws Exception {
        return fileRepository.findById(id).orElseThrow(() -> new Exception("No files found for this id"));
    }

    public List<ResourceDownloadDto> createDownloadDto() throws Exception {
        var files = fileRepository.findByFileType();
        var resources = new ArrayList<ResourceDownloadDto>();
        for (var fileData : files) {
            var resource = minioService.downloadFile(fileData.getPath() + "/" + fileData.getName(), fileData.isBase());
            resources.add(new ResourceDownloadDto(fileData.getId(), resource, fileData.getType()));
        }
        return resources;
    }

    public List<FileData> getFileDataByPage(String page) {
        return fileRepository.findByPathStart(page);
    }

    public abstract void deleteResource(Long id) throws Exception;

    public abstract ResourceType getType();

    public abstract void saveResource(ResourceData resourceData) throws Exception;
}
