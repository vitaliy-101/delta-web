package org.example.deltawebfacade.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.file.FileRequest;
import org.example.deltawebfacade.dto.path.PathRequest;
import org.example.deltawebfacade.kafka.dtos.DeleteResourceDto;
import org.example.deltawebfacade.mapper.KafkaDtoMapper;
import org.example.deltawebfacade.service.file.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/delta/pages")
public class FileController {
    private final KafkaDtoMapper kafkaDtoMapper;
    private final FileService fileService;

    @Operation(summary = "Загрузка одного или нескольких файлов", description = "Передаем параметры файлов (расположение, автора и тд)" +
            " Некоторые параметры указывать не обязтаельно. Метод выполнять в Postman")
    @PostMapping(path = "/{page}/file")
    public void saveFile(@PathVariable(name = "page") String page,
                                     @RequestPart("fileRequest") @Valid FileRequest fileRequest,
                                     @RequestPart("files") @Parameter(description = "файлы") List<MultipartFile> files) {
        fileService.saveFiles(kafkaDtoMapper.convertToFileKafkaDto(page, fileRequest, files));
    }

    @Operation(summary = "Метод для создания папок", description = "Передаем так для всех страниц, тк можем отправлять json с любыми параметрами")
    @PostMapping(path = "/{page}/path")
    public void savePath(@PathVariable(name = "page") String page,
                         @RequestPart("pathRequest") @Valid PathRequest pathRequest,
                         @RequestPart(value = "file", required = false) @Parameter(description = "файл") MultipartFile file) {
        if (file == null) {
            fileService.savePath(kafkaDtoMapper.convertToPathKafkaDto(page, pathRequest));
        } else {
            fileService.savePath(kafkaDtoMapper.convertToPathKafkaDto(page, pathRequest, List.of(file)));
        }
    }

    @Operation(summary = "Удалить файл по id")
    @DeleteMapping("/delete/file/{fileId}")
    public void deleteFileById(@PathVariable("fileId") Long fileId) {
        fileService.deleteResourceById(new DeleteResourceDto("file", fileId));
    }

    @Operation(summary = "Удалить папку по id")
    @DeleteMapping("/delete/path/{fileId}")
    public void deletePathById(@PathVariable("fileId") Long fileId) {
        fileService.deleteResourceById(new DeleteResourceDto("path", fileId));
    }

    @Operation(summary = "Все файлы для странички",
            description = "Получаем дерево папок и файлов, которые в начале своего path содержат {page}/")
    @GetMapping("/{page}")
    public ResponseEntity<String> getFilesByPage(@PathVariable("page") String page) {
        return ResponseEntity.ok(fileService.getPathResponse(page));
    }

    @Operation(summary = "Скачать файл по ссылке по id", description = "Получаем в ответе файл для скачивания")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileId") Long fileId) {
        var file = fileService.getDownloadFile(String.valueOf(fileId));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileUpload; filename=\""+ URLEncoder.encode(String.valueOf(file.getId()), StandardCharsets.UTF_8)
                        +"\"").body(new ByteArrayResource(file.getData()));
    }
}
