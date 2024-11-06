package org.example.deltawebfacade.kafka.consumer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.deltawebfacade.kafka.dtos.ResourceDownloadDto;
import org.example.deltawebfacade.redis.PathInfoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class KafkaListeners {
    private final PathInfoService pathInfoService;
    @KafkaListener(
            topics = "page-resources",
            groupId = "page",
            containerFactory = "resourcePageFactory"
    )
    public void resourcePageListener(ConsumerRecord<String, String> record) throws Exception {
        var page = record.key();
        var response = record.value();
        pathInfoService.savePathResponse(page, response);
    }

    @KafkaListener(
            topics = "download-resource",
            groupId = "download",
            containerFactory = "resourceDownloadFactory"
    )
    public void downloadListener(ResourceDownloadDto resourceDownloadDto) throws Exception {
        pathInfoService.saveDownloadFileResponse(resourceDownloadDto);
    }
}