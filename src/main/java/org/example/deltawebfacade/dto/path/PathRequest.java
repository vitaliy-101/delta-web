package org.example.deltawebfacade.dto.path;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.FileRequest;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Параметры папки, схожи с файлом")
public class PathRequest extends FileRequest {
    @Schema(description = "Имя папки")
    private String name;
    @Schema(description = "Находится ли в базе знаний", defaultValue = "false")
    private Boolean isBase;
}