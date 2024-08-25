package org.example.deltawebfacade.model.file;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private Integer type;
    @Column(name = "type_str")
    private String typeStr;
    @Column(name = "file_data")
    private byte[] fileData;
    @Column(name = "path")
    private String path;
    @Column(name = "author")
    private String author;
    @Column(name = "creation_date")
    private String creationDate;
    @Column(name = "year")
    private Integer year;
    @Column(name = "is_base")
    private Boolean isBase;


}
