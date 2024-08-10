package org.example.deltawebfacade.dto.file;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Schema(description = "Сущность файла")
public class FileRequest {
    @Schema(description = "Папка, в которой находится, в формате 'папка/еще_одна/тд/' ")
    private String path;
}
