package com.example.testkafka.kafka.consumer;

import com.example.testkafka.file_system.ResourceData;
import com.example.testkafka.file_system.strategy.FileService;
import com.example.testkafka.file_system.strategy.ResourceService;
import com.example.testkafka.kafka.dtos.DeleteResourceDto;
import com.example.testkafka.kafka.dtos.file.FileKafkaDto;
import com.example.testkafka.kafka.dtos.path.PathKafkaDto;
import com.example.testkafka.mapper.FileEntityMapper;
import com.example.testkafka.mapper.ResourceDataMapper;
import com.example.testkafka.minio.MinioService;
import com.example.testkafka.repository.FileRepository;
import com.example.testkafka.service.KafkaService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class KafkaListeners {
    private final ResourceDataMapper resourceDataMapper;
    private final KafkaService kafkaService;

    @KafkaListener(
            topics = "file-save",
            groupId = "resource-save",
            containerFactory = "fileFactory"
    )
    public void fileSaveListener(FileKafkaDto fileDto) throws Exception {
        kafkaService.saveResource(resourceDataMapper.convertFromFileDto(fileDto));
    }

    @KafkaListener(
            topics = "path-save",
            groupId = "resource-save",
            containerFactory = "pathFactory"
    )
    public void pathSaveListener(PathKafkaDto pathDto) throws Exception {
        kafkaService.saveResource(resourceDataMapper.convertFromPathDto(pathDto));
    }

    @KafkaListener(
            topics = "resource-delete",
            groupId = "resource-delete",
            containerFactory = "deleteResourceFactory"
    )
    public void deleteResourceListener(DeleteResourceDto deleteResourceDto) throws Exception {
        kafkaService.deleteResource(resourceDataMapper.convertFromDeleteDto(deleteResourceDto));
    }


}
