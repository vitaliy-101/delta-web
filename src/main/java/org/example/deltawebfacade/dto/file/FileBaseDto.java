package org.example.deltawebfacade.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileBaseDto {
    private Long id;
    private String name;
    private Integer type;
    private String downloadURL;
}
