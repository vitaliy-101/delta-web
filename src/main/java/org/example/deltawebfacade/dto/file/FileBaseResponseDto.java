package org.example.deltawebfacade.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileBaseResponseDto extends FileBaseDto {
    private String downloadURL;
    private String path;
    private Long id;
    private String creationDate;
}
