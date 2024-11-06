package org.example.deltawebfacade.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.deltawebfacade.exceptions.NotFoundByIdException;
import org.example.deltawebfacade.exceptions.NotFoundByPageException;
import org.example.deltawebfacade.kafka.dtos.ResourceDownloadDto;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PathInfoService {
    private final RedisTemplate<String, String> pageRedisTemplate;
    private final RedisTemplate<String, ResourceDownloadDto> downloadRedisTemplate;

    public void savePathResponse(String page, String response) {
        pageRedisTemplate.opsForValue().set(page, response);
    }

    public String getPathResponse(String page) {
        try {
            return pageRedisTemplate.opsForValue().get(page);
        }
        catch (Exception e) {
            log.error("No files found on this page");
            throw new NotFoundByPageException();
        }
    }

    public void saveDownloadFileResponse(ResourceDownloadDto resourceDownloadDto) {
        downloadRedisTemplate.opsForValue().set(String.valueOf(resourceDownloadDto.getId()), resourceDownloadDto);
    }

    public ResourceDownloadDto getDownloadFile(String id) {
        var resourceDownloadDto = downloadRedisTemplate.opsForValue().get(id);
        if (resourceDownloadDto == null) {
            log.error("No files found for this id");
            throw new NotFoundByIdException(ResourceDownloadDto.class, Long.valueOf(id));
        }
        return resourceDownloadDto;
    }
}
