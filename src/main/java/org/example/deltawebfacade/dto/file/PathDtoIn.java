package org.example.deltawebfacade.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PathDtoIn {
    @Schema(description = "Папка, в которой находится, в формате 'папка/еще_одна/тд/' ")
    private String path;
    private List<PathParams> files;
}
