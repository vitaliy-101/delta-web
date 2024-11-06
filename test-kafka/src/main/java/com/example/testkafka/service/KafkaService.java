package com.example.testkafka.service;

import com.example.testkafka.constants.PageTypes;
import com.example.testkafka.file_system.ResourceData;
import com.example.testkafka.file_system.ResourceDeleteData;
import com.example.testkafka.file_system.generator.FileSystemGenerator;
import com.example.testkafka.file_system.generator.PathResponse;
import com.example.testkafka.file_system.strategy.FileService;
import com.example.testkafka.file_system.strategy.PathService;
import com.example.testkafka.file_system.strategy.ResourceManager;
import com.example.testkafka.file_system.strategy.ResourceService;
import com.example.testkafka.kafka.dtos.ResourceDownloadDto;
import com.example.testkafka.mapper.FileEntityMapper;
import com.example.testkafka.minio.MinioService;
import com.example.testkafka.repository.FileRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final FileEntityMapper fileEntityMapper;
    private final MinioService minioService;
    private final FileRepository fileRepository;
    private final FileSystemGenerator fileSystemGenerator;
    private final KafkaTemplate<String, String> resourcePageTemplate;
    private final KafkaTemplate<String, ResourceDownloadDto> downloadResourceTemplate;
    private final ObjectMapper objectMapper;

    public void saveResource(ResourceData resourceData) throws Exception {
        var resourceServices = createResourceServices();
        var resourceManger = new ResourceManager(resourceServices);
        resourceManger.saveResource(resourceData);
    }

    public void deleteResource(ResourceDeleteData resourceData) throws Exception {
        var resourceServices = createResourceServices();
        var resourceManger = new ResourceManager(resourceServices);
        resourceManger.deleteResource(resourceData);
    }

    private List<ResourceService> createResourceServices() {
        return List.of(new FileService(fileEntityMapper, fileRepository, minioService),
                new PathService(fileEntityMapper, fileRepository, minioService));
    }

    @Scheduled(fixedRate = 3000)
    public void getResourceByPage() {
        for (var page : PageTypes.PAGES) {
            try {
                var jsonResponse = objectMapper.writeValueAsString(fileSystemGenerator.generate(page, createResourceServices().getFirst().getFileDataByPage(page)));
                resourcePageTemplate.send("page-resources", page, jsonResponse);
            } catch (JsonProcessingException e) {
                System.err.println("Error serializing PathResponse: " + e.getMessage());
            }
        }
    }

    @Scheduled(fixedRate = 3000)
    public void sendDownloadResource() throws Exception {
        createResourceServices().getFirst().createDownloadDto()
                .forEach(resource -> downloadResourceTemplate.send("download-resource", resource));
    }


}
