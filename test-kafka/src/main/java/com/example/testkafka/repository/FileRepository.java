package com.example.testkafka.repository;

import com.example.testkafka.model.FileData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<FileData, Long> {
    @Query(value = "SELECT f FROM FileData f WHERE f.path LIKE CONCAT(:page,'%')")
    List<FileData> findByPage(String page);

    @Query(value = "SELECT f FROM FileData f WHERE f.type != 'path'")
    List<FileData> findByFileType();

    @Transactional
    @Modifying
    @Query("DELETE FROM FileData f WHERE f.id IN :ids")
    void deletePathsByIds(@Param("ids") List<Long> ids);

    @Transactional
    @Modifying
    @Query("DELETE FROM FileData f WHERE f.path LIKE CONCAT(:path,'%')")
    void deleteFilesByPathStart(@Param("path") String path);

    @Query(value = "SELECT f FROM FileData f WHERE f.path LIKE CONCAT(:pathName,'%')")
    List<FileData> findByPathStart(String pathName);

    @Query(value = "SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FileData f WHERE f.path LIKE CONCAT(:pathName,'%')")
    boolean existByPathStart(String pathName);

    @Query(value = "SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM FileData f WHERE f.path = :pathName AND f.type = 'path'")
    boolean existPathByPathStart(String pathName);

    @Query(value = "SELECT f FROM FileData f WHERE f.path = :path AND f.name = :fileName")
    FileData selectExistFile(String path, String fileName);



}
