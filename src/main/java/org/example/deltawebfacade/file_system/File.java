package org.example.deltawebfacade.file_system;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private String name;
    private Integer type;
    private String path;
    private String author;
    private Integer year;
    private String creationDate;
    private String downloadURL;

}