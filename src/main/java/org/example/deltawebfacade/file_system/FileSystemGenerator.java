package org.example.deltawebfacade.file_system;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.file.library.FileLibraryResponse;
import org.example.deltawebfacade.dto.file.library.PathLibraryResponse;
import org.example.deltawebfacade.mapper.DtoConverter;
import org.example.deltawebfacade.mapper.PathFileConverter;
import org.example.deltawebfacade.model.gallery.FileData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@RequiredArgsConstructor
public class FileSystemGenerator {
    private final PathFileConverter pathFileConverter;
    public Folder convertFilesToFolderStructure(List<File> files, Folder folder) {
        folder.setFiles(new ArrayList<>());
        folder.setFolders(new ArrayList<>());
        for (File file : files) {
            String pathName = folder.getPath() + "/" + folder.getName();
            if (folder.getPath().isEmpty()) {
                pathName = folder.getName();
            }
            if (file.getPath().equals(pathName)) {
                if (file.getType() != 0) {
                    folder.addFile(file);
                    continue;
                }
                Folder currentFolder = new Folder();
                final String pathNameFinal = pathName;
                List<File> currentFiles = files
                        .stream()
                        .filter(currentFile -> currentFile.getPath().startsWith(pathNameFinal))
                        .toList();
                currentFolder.setPath(file.getPath());
                currentFolder.setName(file.getName());
                currentFolder.setType(0);
                currentFolder.setCountFiles(currentFiles.size());
                folder.addFolder(convertFilesToFolderStructure(currentFiles, currentFolder));
            }
        }
        return folder;
    }

    public PathLibraryResponse convertFilesToLibraryFolderStructure(List<FileLibraryResponse> files, PathLibraryResponse folder) {
        folder.setFiles(new ArrayList<>());
        folder.setFolders(new ArrayList<>());
        for (FileLibraryResponse file : files) {
            String pathName = folder.getPath() + "/" + folder.getName();
            if (folder.getPath().isEmpty()) {
                pathName = folder.getName();
            }
            if (file.getPath().equals(pathName)) {
                if (file.getType() != 0) {
                    file.setDownloadURL(pathFileConverter.getDownloadUrl(file, "library"));
                    folder.addFile(file);
                    continue;
                }
                final String pathNameFinal = pathName + "/" + file.getName();
                List<FileLibraryResponse> currentFiles = files
                        .stream()
                        .filter(currentFile -> currentFile.getPath().startsWith(pathNameFinal))
                        .toList();
                PathLibraryResponse currentFolder = pathFileConverter.simpleConvert(file, PathLibraryResponse.class);
                currentFolder.setCountFiles(currentFiles.size());
                folder.addFolder(convertFilesToLibraryFolderStructure(currentFiles, currentFolder));
            }
        }
        return folder;
    }
}

