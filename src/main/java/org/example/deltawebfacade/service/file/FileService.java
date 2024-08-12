package org.example.deltawebfacade.service.file;

import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.constants.MediaTypeAssociation;
import org.example.deltawebfacade.dto.file.FileParams;
import org.example.deltawebfacade.dto.file.gallery.FileGalleryResponse;
import org.example.deltawebfacade.dto.file.paper.FilePaperResponse;
import org.example.deltawebfacade.dto.path.PathParams;
import org.example.deltawebfacade.dto.path.PathResponse;
import org.example.deltawebfacade.dto.file.library.FileLibraryResponse;
import org.example.deltawebfacade.dto.path.gallery.PathGalleryResponse;
import org.example.deltawebfacade.dto.path.library.PathLibraryResponse;
import org.example.deltawebfacade.dto.path.paper.PathPaperResponse;
import org.example.deltawebfacade.exceptions.NotFoundByIdException;
import org.example.deltawebfacade.file_system.FileSystemGenerator;
import org.example.deltawebfacade.mapper.DtoConverter;
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

    private PathResponse createBasePathResponse(Integer countFiles, String page) {
        PathResponse pathResponse = new PathResponse();
        pathResponse.setName(page);
        pathResponse.setPath("");
        pathResponse.setType(0);
        pathResponse.setCountFiles(countFiles);
        return pathResponse;
    }


    public List<FileData> uploadFiles(FileParams fileParams, List<MultipartFile> files) throws Exception {
        List<FileData> fileDataList = new ArrayList<>();
        for (MultipartFile file : files) {
            fileDataList.add(uploadFile(fileParams, file));
        }
        return fileDataList;
    }

    public FileData uploadFile(FileParams fileParams, MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..") || fileName.contains("/")) {
                throw new Exception("The file name is invalid" + fileName);
            }
            FileData fileData = fileRepository.save(FileData.builder()
                    .name(fileName)
                    .type(getIntegerType(file.getContentType()))
                    .typeStr(file.getContentType())
                    .fileData(FileUtils.compressFile(file.getBytes()))
                    .path(fileParams.getPage() + fileParams.getPath())
                    .author(fileParams.getAuthor())
                    .year(fileParams.getYear())
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
                .typeStr("Path")
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
        return fileSystemGenerator.convertFilesToLibraryFolderStructure(
                dtoConverter.simpleConvert(fileDataList, FileLibraryResponse.class),
                dtoConverter.simpleConvert(createBasePathResponse(fileDataList.size(), page), PathLibraryResponse.class)
        );
    }

    public PathGalleryResponse getFilesGallery(String page) {
        List<FileData> fileDataList = getAllFilesByPage(page);
        return fileSystemGenerator.convertFilesToGalleryFolderStructure(
                dtoConverter.simpleConvert(fileDataList, FileGalleryResponse.class),
                dtoConverter.simpleConvert(createBasePathResponse(fileDataList.size(), page), PathGalleryResponse.class)
        );
    }

    public PathPaperResponse getFilesPaper(String page) {
        List<FileData> fileDataList = getAllFilesByPage(page);
        return fileSystemGenerator.convertFilesToPaperFolderStructure(
                dtoConverter.simpleConvert(fileDataList, FilePaperResponse.class),
                dtoConverter.simpleConvert(createBasePathResponse(fileDataList.size(), page), PathPaperResponse.class)
        );
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
