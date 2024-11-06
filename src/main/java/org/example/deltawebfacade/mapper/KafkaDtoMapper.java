package org.example.deltawebfacade.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.deltawebfacade.dto.file.FileRequest;
import org.example.deltawebfacade.dto.path.PathRequest;
import org.example.deltawebfacade.exceptions.FileNullException;
import org.example.deltawebfacade.kafka.dtos.FileKafkaResource;
import org.example.deltawebfacade.kafka.dtos.file.FileKafkaDto;
import org.example.deltawebfacade.kafka.dtos.path.PathKafkaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KafkaDtoMapper {
    @Mapping(target = "fileKafkaResources", qualifiedByName = "convertToFileKafkaResourceList", source = "files")
    @Mapping(target = "path", source = "fileRequest.path")
    @Mapping(target = "author", source = "fileRequest.author")
    @Mapping(target = "year", source = "fileRequest.year")
    FileKafkaDto convertToFileKafkaDto(String page, FileRequest fileRequest, List<MultipartFile> files);

    @Mapping(target = "fileKafkaResources", qualifiedByName = "convertToFileKafkaResourceList", source = "files")
    @Mapping(target = "path", qualifiedByName = "setPathForPathDto", source = "pathRequest")
    @Mapping(target = "author", source = "pathRequest.author")
    @Mapping(target = "year", source = "pathRequest.year")
    @Mapping(target = "isBase", source = "pathRequest.isBase")
    PathKafkaDto convertToPathKafkaDto(String page, PathRequest pathRequest, List<MultipartFile> files);

    @Mapping(target = "path", qualifiedByName = "setPathForPathDto", source = "pathRequest")
    @Mapping(target = "author", source = "pathRequest.author")
    @Mapping(target = "year", source = "pathRequest.year")
    @Mapping(target = "isBase", source = "pathRequest.isBase")
    PathKafkaDto convertToPathKafkaDto(String page, PathRequest pathRequest);

    @Named("setPathForPathDto")
    default String setPathForPathDto(PathRequest pathRequest) {
        return pathRequest.getPath() + "/" + pathRequest.getName();
    }

    @Named("convertToFileKafkaResourceList")
    default List<FileKafkaResource> convertToFileKafkaResourceList(List<MultipartFile> files) {
        var fileKafkaResources = new ArrayList<FileKafkaResource>();
        for (var multipartFile : files) {
            var fileKafkaResource = convertToFileKafkaResource(multipartFile);
            fileKafkaResources.add(fileKafkaResource);
        }
        return fileKafkaResources;
    }

    @Named("convertToFileKafkaResource")
    default FileKafkaResource convertToFileKafkaResource(MultipartFile multipartFile) {
        try {
            return FileKafkaResource.builder()
                    .content(multipartFile.getBytes())
                    .name(multipartFile.getName())
                    .originalFilename(multipartFile.getOriginalFilename())
                    .contentType(multipartFile.getContentType())
                    .build();
        }
        catch (Exception e) {
            throw new FileNullException();
        }
    }
}
