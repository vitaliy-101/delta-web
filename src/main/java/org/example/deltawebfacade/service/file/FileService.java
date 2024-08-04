package org.example.deltawebfacade.service.file;

import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.file.FileRequest;
import org.example.deltawebfacade.exceptions.NotFoundByIdException;
import org.example.deltawebfacade.model.gallery.FileData;
import org.example.deltawebfacade.repository.FileRepository;
import org.example.deltawebfacade.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    public FileData uploadFile(String page, FileRequest fileRequest) throws Exception {
        MultipartFile file = fileRequest.getFile();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new Exception("The file name is invalid" + fileName);
            }
            FileData fileData = fileRepository.save(FileData.builder()
                    .name(fileName)
                    .type(file.getContentType())
                    .fileData(FileUtils.compressFile(file.getBytes()))
                    .path(page + "/" + fileRequest.getPath()).build());
            return fileRepository.save(fileData);
        } catch (Exception e) {
            throw new Exception("File could not be save");
        }

    }

    public FileData downloadFile(String id) throws Exception {
        return fileRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundByIdException(FileService.class, Long.valueOf(id)));
    }

    public List<FileData> getAllFilesByPage(String page) {
        return fileRepository.findByPage(page);
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
