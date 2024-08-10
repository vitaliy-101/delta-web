package org.example.deltawebfacade.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathResponse {
    private String name;
    private String path;
    private Integer type;
    private String creationDate;
}
