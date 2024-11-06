package org.example.deltawebfacade.dto.file;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Параметры файла")
public class FileRequest {
    @Schema(description = "Расположение файла. Если файлы вложены в папки на страничке: /path1/path2, " +
            "Если расположение пустое (т.е.) файл находится на уровне отображения странички, то оставляем только кавычки. Также " +
            "названия папок в себе не содержат лишних '/' (именно в имени)")
    @Pattern(regexp = "^(/[^/]+[^/])?$", message = "Path is incorrectly specified")
    private String path;
    @Schema(description = "Указываем автора, если требуется")
    private String author;
    @Schema(description = "Указываем год, если требуется")
    private Integer year;
}
