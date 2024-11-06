package com.example.testkafka.file_system.strategy;

import com.example.testkafka.file_system.MinioResource;
import com.example.testkafka.file_system.ResourceData;
import com.example.testkafka.file_system.ResourceType;
import com.example.testkafka.mapper.FileEntityMapper;
import com.example.testkafka.minio.MinioService;
import com.example.testkafka.model.FileData;
import com.example.testkafka.repository.FileRepository;
import org.springframework.stereotype.Component;

@Component
public class PathService extends ResourceService {

    public PathService(FileEntityMapper fileEntityMapper, FileRepository fileRepository, MinioService minioService) {
        super(fileEntityMapper, fileRepository, minioService);
    }

    private MinioResource savePathToStoreAndCreateMinioFile(ResourceData resourceData) {
        var fileData = fileEntityMapper.convertFromResourceData(resourceData);
        if (fileData.isBase()) {
            return saveFilesToStoreAndCreateMinioFiles(resourceData).getFirst();
        }
        createNecessaryPaths(fileData);
        return fileEntityMapper.convertFromFileDataToMinioResource(fileData);
    }

    @Override
    public void deleteResource(Long id) throws Exception {
        var fileData = findResourceById(id);
        fileRepository.deleteFilesByPathStart(fileData.getPath());
        minioService.deletePath(fileEntityMapper.convertFromFileDataToMinioResource(fileData));
    }

    @Override
    public ResourceType getType() {
        return ResourceType.PATH;
    }

    @Override
    public void saveResource(ResourceData resourceData) throws Exception {
        var minioResource = savePathToStoreAndCreateMinioFile(resourceData);
         if (minioResource.isBase()) {
             minioService.uploadFile(minioResource);
         }
    }
}
