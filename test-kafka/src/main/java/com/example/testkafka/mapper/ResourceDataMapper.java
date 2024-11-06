package com.example.testkafka.mapper;

import com.example.testkafka.file_system.ResourceData;
import com.example.testkafka.file_system.ResourceDeleteData;
import com.example.testkafka.file_system.ResourceType;
import com.example.testkafka.kafka.dtos.DeleteResourceDto;
import com.example.testkafka.kafka.dtos.file.FileKafkaDto;
import com.example.testkafka.kafka.dtos.path.PathKafkaDto;
import org.apache.kafka.common.protocol.types.Field;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceDataMapper {

    @Mapping(target = "resourceType", qualifiedByName = "setFileType", source = ".")
    ResourceData convertFromFileDto(FileKafkaDto fileKafkaDto);

    @Mapping(target = "resourceType", qualifiedByName = "setPathType", source = ".")
    ResourceData convertFromPathDto(PathKafkaDto pathKafkaDto);

    @Mapping(target = "resourceType", qualifiedByName = "setTypeByDeleteType", source = "deleteResourceDto.type")
    ResourceDeleteData convertFromDeleteDto(DeleteResourceDto deleteResourceDto);

    @Named("setTypeByDeleteType")
    default ResourceType setTypeByDeleteType(String deleteType) {
        if (deleteType.equals("path")) {
            return ResourceType.PATH;
        }
        return ResourceType.FILE;
    }

    @Named("setPathType")
    default ResourceType setPathType(PathKafkaDto pathKafkaDto) {
        return ResourceType.PATH;
    }

    @Named("setFileType")
    default ResourceType setFileType(FileKafkaDto fileKafkaDto) {
        return ResourceType.FILE;
    }
}
