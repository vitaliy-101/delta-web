package org.example.deltawebfacade.service.file;

import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.kafka.dtos.DeleteResourceDto;
import org.example.deltawebfacade.kafka.dtos.ResourceDownloadDto;
import org.example.deltawebfacade.kafka.dtos.file.FileKafkaDto;
import org.example.deltawebfacade.kafka.dtos.path.PathKafkaDto;
import org.example.deltawebfacade.redis.PathInfoService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final KafkaTemplate<String, FileKafkaDto> fileKafkaTemplate;
    private final KafkaTemplate<String, PathKafkaDto> pathKafkaTemplate;
    private final KafkaTemplate<String, DeleteResourceDto> deleteResourceKafkaTemplate;
    private final PathInfoService pathInfoService;

    public void saveFiles(FileKafkaDto fileKafkaDto) {
        fileKafkaTemplate.send("file-save", fileKafkaDto);
    }

    public void savePath(PathKafkaDto pathKafkaDto) {
        pathKafkaTemplate.send("path-save", pathKafkaDto);
    }

    public void deleteResourceById(DeleteResourceDto deleteResourceDto) {
        deleteResourceKafkaTemplate.send("resource-delete", deleteResourceDto);
    }

    public String getPathResponse(String page) {
        return pathInfoService.getPathResponse(page);
    }

    public ResourceDownloadDto getDownloadFile(String id) {
        return pathInfoService.getDownloadFile(id);
    }
}
