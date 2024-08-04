package org.example.deltawebfacade.mapper;

import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.file.FileBaseDto;
import org.example.deltawebfacade.dto.file.FileResponseDespatch;
import org.example.deltawebfacade.dto.file.FileResponseUponReceipt;
import org.example.deltawebfacade.model.gallery.FileData;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PathFileConverter extends DtoConverter {

    public List<FileResponseDespatch> convertFromModelList(List<FileData> fileDataList, String page) {
        List<FileResponseDespatch> fileResponseDespatchList = simpleConvert(fileDataList, FileResponseDespatch.class);
        for (FileResponseDespatch fileResponseDespatch : fileResponseDespatchList) {
            String downloadUrl = getDownloadUrl(fileResponseDespatch, page);
            fileResponseDespatch.setDownloadURL(downloadUrl);
        }
        return fileResponseDespatchList;
    }

    public FileResponseUponReceipt convertFromModel(FileData fileData, String page, MultipartFile file) {
        FileResponseUponReceipt fileResponseUponReceipt = simpleConvert(fileData, FileResponseUponReceipt.class);
        fileResponseUponReceipt.setSize(file.getSize());
        fileResponseUponReceipt.setDownloadURL(getDownloadUrl(fileResponseUponReceipt, page));
        return fileResponseUponReceipt;
    }

    private String getDownloadUrl(FileBaseDto fileBaseDto, String page) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/delta/path-pages/")
                .path(page)
                .path("/download/")
                .path(String.valueOf(fileBaseDto.getId()))
                .toUriString();
    }

}