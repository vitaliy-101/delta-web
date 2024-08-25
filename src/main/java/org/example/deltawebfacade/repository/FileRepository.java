package org.example.deltawebfacade.repository;

import org.example.deltawebfacade.model.file.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileData, Long> {
    Optional<FileData> findByName(String name);
    @Query(value = "SELECT f FROM FileData f WHERE f.path LIKE CONCAT(:page,'%')")
    List<FileData> findByPage(String page);

    @Query(value = "SELECT f FROM FileData f WHERE f.isBase = true")
    List<FileData> findByBaseType();
}
