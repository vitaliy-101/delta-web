package org.example.deltawebfacade.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.file.*;
import org.example.deltawebfacade.dto.file.library.PathLibraryResponse;
import org.example.deltawebfacade.file_system.Folder;
import org.example.deltawebfacade.mapper.PathFileConverter;
import org.example.deltawebfacade.model.gallery.FileData;
import org.example.deltawebfacade.service.file.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.List;
//типы файлов: 0 - папка
//path имеет ввид - /psasd/asdasd
@RestController()
@RequiredArgsConstructor
@RequestMapping("/delta/path-pages")
@Tag(name = "Работа с файлами")
public class PathFileController {
    private final FileService fileService;
    private final PathFileConverter pathFileConverter;

    @Operation(summary = "Загрузка одного или нескольких файлов для library", description = "В данном случае должны указать автора")
    @PostMapping(path = "/library", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<FileResponseUponReceipt> saveFileLibrary(@RequestBody @ModelAttribute FileRequestLibrary fileRequestLibrary) throws Exception {
        List<MultipartFile> files = fileRequestLibrary.getFiles();
        PathParams pathParams = new PathParams("library", fileRequestLibrary.getAuthor(), 0, fileRequestLibrary.getPath(), "Empty");
        List<FileData> fileData = fileService.uploadFiles(pathParams, files);
        return pathFileConverter.convertFromModelList(pathParams, fileData);
    }

    @Operation(summary = "Метод для создания папок", description = "Передаем так для всех страниц, тк можем отправлять json с любыми параметрами")
    @PostMapping(path = "/{page}")
    public PathResponse savePath(@PathVariable("page") String page,
                                 @RequestBody PathRequest pathRequest) {
        PathParams pathParams = pathFileConverter.convertFromPathRequest(pathRequest, page);
        FileData fileData = fileService.uploadPath(pathParams);
        return pathFileConverter.simpleConvert(fileData, PathResponse.class);
    }


    @Operation(summary = "Скачать файл по ссылке по id", description = "Получаем в ответе файл для скачивания")
    @GetMapping("/{page}/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId, @PathVariable("page") String page) throws Exception {
        FileData file = fileService.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType().toString()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileUpload; filename=\""+ URLEncoder.encode(file.getName(), "UTF-8")
                        +"\"").body(new ByteArrayResource(file.getFileData()));
    }

    @Operation(summary = "НЕ ИСПОЛЬЗУЕТСЯ. Получить все файлы для странички", description = "Получаем все файлы, которые должны лежать на данной страничке," +
            "формат: id, имя, тип, ссылка для скачивания, папка в которой лежит")
    @GetMapping("/{page}")
    public List<FileResponseDespatch> getAllFilesByPage(@PathVariable("page") String page) {
        List<FileData> fileData = fileService.getAllFilesByPage(page);
        return pathFileConverter.convertFromModelList(fileData, page);
    }

    @GetMapping("/library")
    public PathLibraryResponse getAllFilesLibrary() {
        return fileService.getFilesLibrary("library");
    }

    @Operation(summary = "Удалить файл по id")
    @DeleteMapping("/{page}/delete/{fileId}")
    public void deleteFileById(@PathVariable("fileId") String fileId, @PathVariable("page") String page) {
        fileService.deleteFileById(Long.valueOf(fileId));
    }


}
