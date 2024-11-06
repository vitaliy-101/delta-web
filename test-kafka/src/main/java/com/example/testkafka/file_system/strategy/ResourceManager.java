package com.example.testkafka.file_system.strategy;

import com.example.testkafka.file_system.ResourceData;
import com.example.testkafka.file_system.ResourceDeleteData;
import com.example.testkafka.file_system.ResourceType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ResourceManager {
    private final Map<ResourceType, ResourceService> resourceServiceMap;

    public ResourceManager(List<ResourceService> resourceServices) {
        resourceServiceMap = new HashMap<>();
        resourceServices.forEach(resourceService -> resourceServiceMap.put(resourceService.getType(), resourceService));
    }

    private ResourceService getResourceServiceByType(ResourceType resourceType) throws Exception {
        if (!resourceServiceMap.containsKey(resourceType)) {
            log.error("Map doesnt have this resourceType");
            throw new Exception("Map doesnt have this resourceType");
        }
        return resourceServiceMap.get(resourceType);
    }

    public void saveResource(ResourceData resourceData) throws Exception {
        getResourceServiceByType(resourceData.getResourceType()).saveResource(resourceData);
    }

    public void deleteResource(ResourceDeleteData resourceData) throws Exception {
        getResourceServiceByType(resourceData.getResourceType()).deleteResource(resourceData.getId());
    }

}
