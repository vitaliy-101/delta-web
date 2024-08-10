package org.example.deltawebfacade.dto.file.library;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.PathFileBaseResponse;
import org.example.deltawebfacade.file_system.Folder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathLibraryResponse extends PathFileBaseResponse {
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
