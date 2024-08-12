package org.example.deltawebfacade.dto.path.gallery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.gallery.FileGalleryResponse;
import org.example.deltawebfacade.dto.file.library.FileLibraryResponse;
import org.example.deltawebfacade.dto.path.PathResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathGalleryResponse extends PathResponse {
    private Integer year;
    private List<FileGalleryResponse> files;
    private List<PathGalleryResponse> folders;

    public void addFile(FileGalleryResponse file) {
        files.add(file);
    }

    public void addFolder(PathGalleryResponse folder) {
        folders.add(folder);
    }
}