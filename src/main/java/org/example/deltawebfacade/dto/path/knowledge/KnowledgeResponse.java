package org.example.deltawebfacade.dto.path.knowledge;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.knowledge.FileKnowledgeResponse;
import org.example.deltawebfacade.dto.path.PathResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeResponse extends PathResponse {
    private List<FileKnowledgeResponse> files;
    private List<KnowledgeResponse> folders;

    public void addFile(FileKnowledgeResponse file) {
        files.add(file);
    }

    public void addFolder(KnowledgeResponse folder) {
        folders.add(folder);
    }
}