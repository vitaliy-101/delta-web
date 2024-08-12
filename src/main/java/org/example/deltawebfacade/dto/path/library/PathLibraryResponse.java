package org.example.deltawebfacade.dto.path.library;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.library.FileLibraryResponse;
import org.example.deltawebfacade.dto.path.PathResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathLibraryResponse extends PathResponse {
    private String author;
    private List<FileLibraryResponse> files;
    private List<PathLibraryResponse> folders;

    public void addFile(FileLibraryResponse file) {
        files.add(file);
    }

    public void addFolder(PathLibraryResponse folder) {
        folders.add(folder);
    }
}
