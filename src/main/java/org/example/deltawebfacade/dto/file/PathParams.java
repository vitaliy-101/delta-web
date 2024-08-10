package org.example.deltawebfacade.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathParams {
    private String page;
    private String author;
    private Integer year;
    private String path;
    private String name;
}
