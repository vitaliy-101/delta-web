package org.example.deltawebfacade.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.file_system.File;
import org.example.deltawebfacade.file_system.Folder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathFileBaseResponse extends PathResponse {
    private Integer countFiles;
}
