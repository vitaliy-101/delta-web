package org.example.deltawebfacade.model.gallery;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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


}
