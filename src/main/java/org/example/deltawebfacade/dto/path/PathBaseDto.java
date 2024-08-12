package org.example.deltawebfacade.dto.path;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PathBaseDto {
    private String name;
    private String path;
    private Integer type;
    private String creationDate;
}
