package org.example.deltawebfacade.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.file.DeleteResponse;
import org.example.deltawebfacade.dto.file.FileRequest;
import org.example.deltawebfacade.dto.file.FileResponseDespatch;
import org.example.deltawebfacade.dto.file.FileResponseUponReceipt;
import org.example.deltawebfacade.mapper.DtoConverter;
import org.example.deltawebfacade.mapper.PathFileConverter;
import org.example.deltawebfacade.model.gallery.FileData;
import org.example.deltawebfacade.service.file.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/delta/path-pages")
@Tag(name = "Работа с файлами")
public class PathFileController {
    private final FileService fileService;
    private final PathFileConverter pathFileConverter;

    @Operation(summary = "Сохранить файл по ссылке странички", description = "Получаем в ответе сущность сохраненного файла:" +
            "id, имя, тип файла, ссылку для скачивания, размер")
    @PostMapping(path = "/{page}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public FileResponseUponReceipt saveGalleryPhoto(@PathVariable String page, @ModelAttribute FileRequest fileRequest) throws Exception {
        FileData fileData = fileService.uploadFile(page, fileRequest);
        return pathFileConverter.convertFromModel(fileData, page, fileRequest.getFile());
    }

    @Operation(summary = "Скачать файл по ссылке по id", description = "Получаем в ответе файл для скачивания")
    @GetMapping("/{page}/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId, @PathVariable("page") String page) throws Exception {
        FileData file = fileService.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileUpload; filename=\""+ URLEncoder.encode(file.getName(), "UTF-8")
                        +"\"").body(new ByteArrayResource(file.getFileData()));
    }

    @Operation(summary = "Получить все файлы для странички", description = "Получаем все файлы, которые должны лежать на данной страничке," +
            "формат: id, имя, тип, ссылка для скачивания, папка в которой лежит")
    @GetMapping("/{page}")
    public List<FileResponseDespatch> getAllFilesByPage(@PathVariable("page") String page) {
        List<FileData> fileData = fileService.getAllFilesByPage(page);
        return pathFileConverter.convertFromModelList(fileData, page);
    }

    @Operation(summary = "Удалить файл по id")
    @DeleteMapping("/{page}/delete/{fileId}")
    public void deleteFileById(@PathVariable("fileId") String fileId, @PathVariable("page") String page) {
        fileService.deleteFileById(Long.valueOf(fileId));
    }


}
