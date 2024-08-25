package org.example.deltawebfacade.file_system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Folder {
    private String name;
    private String path;
    private Integer type;
    private String author;
    private Integer year;
    private Integer countFiles;
    private List<Folder> folders;
    private List<File> files;

    public void addFile(File file) {
        files.add(file);
    }

    public void addFolder(Folder folder) {
        folders.add(folder);
    }
}