package org.example.deltawebfacade.dto.file.gallery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.FileBaseResponseDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileGalleryResponse extends FileBaseResponseDto {
    private Integer year;
}
