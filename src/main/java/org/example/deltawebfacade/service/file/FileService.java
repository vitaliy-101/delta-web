package org.example.deltawebfacade.service.file;

import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.constants.MediaTypeAssociation;
import org.example.deltawebfacade.dto.file.PathParams;
import org.example.deltawebfacade.dto.file.library.FileLibraryResponse;
import org.example.deltawebfacade.dto.file.library.PathLibraryResponse;
import org.example.deltawebfacade.exceptions.NotFoundByIdException;
import org.example.deltawebfacade.file_system.File;
import org.example.deltawebfacade.file_system.FileSystemGenerator;
import org.example.deltawebfacade.file_system.Folder;
import org.example.deltawebfacade.mapper.DtoConverter;
import org.example.deltawebfacade.mapper.PathFileConverter;
import org.example.deltawebfacade.model.gallery.FileData;
import org.example.deltawebfacade.repository.FileRepository;
import org.example.deltawebfacade.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final DtoConverter dtoConverter;
    private final FileSystemGenerator fileSystemGenerator;

    private Integer getIntegerType(String type) {
        for (Integer integerType : MediaTypeAssociation.integerTypes) {
            if (MediaTypeAssociation.fileTypes.get(integerType).contains(type)) {
                return integerType;
            }
        }
        return 1;
    }

    public List<FileData> uploadFiles(PathParams pathParams, List<MultipartFile> files) throws Exception {
        List<FileData> fileDataList = new ArrayList<>();
        for (MultipartFile file : files) {
            fileDataList.add(uploadFile(pathParams, file));
        }
        return fileDataList;
    }

    public FileData uploadFile(PathParams pathParams, MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..") || fileName.contains("/")) {
                throw new Exception("The file name is invalid" + fileName);
            }
            FileData fileData = fileRepository.save(FileData.builder()
                    .name(fileName)
                    .type(getIntegerType(file.getContentType()))
                    .fileData(FileUtils.compressFile(file.getBytes()))
                    .path(pathParams.getPage() + pathParams.getPath())
                    .author(pathParams.getAuthor())
                    .year(pathParams.getYear())
                    .creationDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime()))
                    .build());
            return fileRepository.save(fileData);
        } catch (Exception e) {
            throw new Exception("File could not be save");
        }
    }

    public FileData uploadPath(PathParams pathParams) {
        FileData fileData = fileRepository.save(FileData.builder()
                .name(pathParams.getName())
                .type(0)
                .path(pathParams.getPage() + pathParams.getPath())
                .author(pathParams.getAuthor())
                .year(pathParams.getYear())
                .creationDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime()))
                .build());
        return fileRepository.save(fileData);
    }

    public FileData downloadFile(String id) throws Exception {
        return fileRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundByIdException(FileService.class, Long.valueOf(id)));
    }

    public List<FileData> getAllFilesByPage(String page) {
        return fileRepository.findByPage(page);
    }

    public PathLibraryResponse getFilesLibrary(String page) {
        List<FileData> fileDataList = getAllFilesByPage(page);
        PathLibraryResponse pathLibraryResponse = new PathLibraryResponse();
        pathLibraryResponse.setName(page);
        pathLibraryResponse.setPath("");
        pathLibraryResponse.setType(0);
        pathLibraryResponse.setCountFiles(fileDataList.size());
        return fileSystemGenerator.convertFilesToLibraryFolderStructure(dtoConverter.simpleConvert(fileDataList, FileLibraryResponse.class), pathLibraryResponse);
    }

    public Boolean existById(Long id) {
        return fileRepository.existsById(id);
    }

    public void deleteFileById(Long id) {
        if (!existById(id)) {
            throw new NotFoundByIdException(FileData.class, id);
        }
        fileRepository.deleteById(id);
    }
}
