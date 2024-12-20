package org.example.deltawebfacade.dto.file.library;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.FileBaseResponseDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileLibraryResponse extends FileBaseResponseDto {
    private String author;
}
