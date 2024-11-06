package com.example.testkafka.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class FileData implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "path")
    private String path;
    @Column(name = "author")
    private String author;
    @Column(name = "creation_date")
    private String creationDate;
    @Column(name = "year")
    private Integer year;
    @Column(name = "is_base")
    private boolean isBase;

}
