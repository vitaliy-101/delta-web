package com.example.testkafka.mapper;

import com.example.testkafka.file_system.MinioResource;
import com.example.testkafka.file_system.ResourceData;
import com.example.testkafka.file_system.generator.FileResponse;
import com.example.testkafka.file_system.generator.PathResponse;
import com.example.testkafka.model.FileData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FileEntityMapper {

    @Mapping(target = "path", qualifiedByName = "createStorePath", source = ".")
    @Mapping(target = "creationDate", qualifiedByName = "createDate", source = ".")
    FileData convertFromResourceData(ResourceData resourceData);

    MinioResource convertFromFileDataToMinioResource(FileData fileData);

    List<FileResponse> convertFromModelListToResponse(List<FileData> fileDataList);

    @Mapping(target = "countFiles", qualifiedByName = "getFileListSize", source = "files")
    PathResponse convertFromFileResponseToPathResponse(FileResponse fileResponse, List<FileResponse> files);

    @Named("getFileListSize")
    default Integer getFileListSize(List<FileResponse> files) {
        return files.size();
    }

    @Named("createStorePath")
    default String createStorePath(ResourceData resourceData) {
        return resourceData.getPage() + resourceData.getPath();
    }

    @Named("createDate")
    default String createDate(ResourceData resourceData) {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());
    }
}
