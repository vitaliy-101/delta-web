package org.example.deltawebfacade.dto.path.paper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.paper.FilePaperResponse;
import org.example.deltawebfacade.dto.path.PathResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathPaperResponse extends PathResponse {
    private List<FilePaperResponse> files;
    private List<PathPaperResponse> folders;

    public void addFile(FilePaperResponse file) {
        files.add(file);
    }

    public void addFolder(PathPaperResponse folder) {
        folders.add(folder);
    }
}