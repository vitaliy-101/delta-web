package com.example.testkafka.file_system;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResourceDeleteData {
    private ResourceType resourceType;
    private Long id;
}
