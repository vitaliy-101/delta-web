package com.example.testkafka.file_system.strategy;

import com.example.testkafka.file_system.MinioResource;
import com.example.testkafka.file_system.ResourceData;
import com.example.testkafka.file_system.ResourceType;
import com.example.testkafka.mapper.FileEntityMapper;
import com.example.testkafka.minio.MinioService;
import com.example.testkafka.repository.FileRepository;
import org.springframework.stereotype.Component;

@Component
public class FileService extends ResourceService {
    public FileService(FileEntityMapper fileEntityMapper, FileRepository fileRepository, MinioService minioService) {
        super(fileEntityMapper, fileRepository, minioService);
    }

    @Override
    public void deleteResource(Long id) throws Exception {
        var fileData = findResourceById(id);
        fileRepository.deleteById(id);
        minioService.deleteFile(fileEntityMapper.convertFromFileDataToMinioResource(fileData));
    }

    @Override
    public ResourceType getType() {
        return ResourceType.FILE;
    }

    @Override
    public void saveResource(ResourceData resourceData) throws Exception {
        var minioResources = saveFilesToStoreAndCreateMinioFiles(resourceData);
        for (MinioResource minioResource : minioResources) {
            minioService.uploadFile(minioResource);
        }
    }
}
