package org.example.deltawebfacade.dto.file.library;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.FileBaseDto;
import org.example.deltawebfacade.dto.file.FileResponse;

import javax.annotation.processing.Generated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileLibraryResponse extends FileResponse {
    private String author;
}
