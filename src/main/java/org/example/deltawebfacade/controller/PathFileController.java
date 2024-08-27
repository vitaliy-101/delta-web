package org.example.deltawebfacade.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.file.*;
import org.example.deltawebfacade.dto.path.gallery.PathGalleryResponse;
import org.example.deltawebfacade.dto.path.knowledge_base.KnowledgeBaseResponse;
import org.example.deltawebfacade.dto.path.library.PathLibraryResponse;
import org.example.deltawebfacade.dto.path.PathBaseDto;
import org.example.deltawebfacade.dto.path.PathParams;
import org.example.deltawebfacade.dto.path.PathRequest;
import org.example.deltawebfacade.dto.path.paper.PathPaperResponse;
import org.example.deltawebfacade.mapper.PathFileConverter;
import org.example.deltawebfacade.model.file.FileData;
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
@RestController()
@RequiredArgsConstructor
@RequestMapping("/delta/path-pages")
@Tag(name = "Работа с файлами")
public class PathFileController {
    private final FileService fileService;
    private final PathFileConverter pathFileConverter;

    @Operation(summary = "Загрузка одного или нескольких файлов", description = "Передаем параметры файлов (расположение, автора и тд)" +
            " Некоторые параметры указывать не обязтаельно. Метод выполнять в Postman")
    @PostMapping(path = "/{page}/file")
    public List<FileResponseUponReceipt> saveFileLibrary(@RequestPart("fileRequest") FileRequest fileRequest,
                                                         @RequestPart("files") @Parameter(description = "файлы") List<MultipartFile> files,
                                                         @PathVariable(name = "page") String page) throws Exception {
        FileParams fileParams = pathFileConverter.convertFromFileRequest(fileRequest, page);
        List<FileData> fileData = fileService.uploadFiles(fileParams, files);
        return pathFileConverter.convertFromModelList(fileParams, fileData);
    }

    @Operation(summary = "Метод для создания папок", description = "Передаем так для всех страниц, тк можем отправлять json с любыми параметрами")
    @PostMapping(path = "/{page}")
    public PathBaseDto savePath(@PathVariable("page") String page,
                                @RequestPart(value = "file", required = false) @Parameter(description = "файл") MultipartFile file,
                                @RequestPart("pathRequest") PathRequest pathRequest) throws Exception {
        PathParams pathParams = pathFileConverter.convertFromPathRequest(pathRequest, page);
        FileData fileData = fileService.uploadPath(pathParams, file);
        return pathFileConverter.simpleConvert(fileData, PathBaseDto.class);
    }


    @Operation(summary = "Скачать файл по ссылке по id", description = "Получаем в ответе файл для скачивания")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId, @PathVariable("page") String page) throws Exception {
        FileData file = fileService.downloadFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getTypeStr()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "fileUpload; filename=\""+ URLEncoder.encode(file.getName(), "UTF-8")
                        +"\"").body(new ByteArrayResource(file.getFileData()));
    }
    @Operation(summary = "Все файлы для странички library",
            description = "Получаем дерево папок и файлов, которые в начале своего path содержат library/")
    @GetMapping("/library")
    public PathLibraryResponse getAllFilesLibrary(@RequestParam(required = false) Boolean isAll) {
        return fileService.getFilesLibrary("library", isAll);
    }

    @Operation(summary = "Все файлы для странички gallery",
            description = "Получаем дерево папок и файлов, которые в начале своего path содержат gallery/")
    @GetMapping("/gallery")
    public PathGalleryResponse getAllFilesGallery() {
        return fileService.getFilesGallery("gallery");
    }

    @Operation(summary = "Все файлы для странички paper",
            description = "Получаем дерево папок и файлов, которые в начале своего path содержат paper/")
    @GetMapping("/paper")
    public PathPaperResponse getAllFilesPaper() {
        return fileService.getFilesPaper("paper");
    }

    @Operation(summary = "Все элементы базы знаний",
            description = "Получаем все элементы из базы знаний")
    @GetMapping("/base")
    public List<KnowledgeBaseResponse> getAllBasePaths() {
        return pathFileConverter.convertFromModelListToKnowledgeResponse(fileService.getAllBasePaths());
    }

    @Operation(summary = "Удалить файл по id")
    @DeleteMapping("/{page}/delete/{fileId}")
    public void deleteFileById(@PathVariable("fileId") String fileId, @PathVariable("page") String page) {
        fileService.deleteFileById(Long.valueOf(fileId));
    }




}
