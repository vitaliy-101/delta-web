package org.example.deltawebfacade.dto.path;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.deltawebfacade.dto.file.FileParams;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathParams extends FileParams {
    private String name;
}
