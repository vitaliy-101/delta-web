package org.example.deltawebfacade.mapper;

import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.file.*;
import org.example.deltawebfacade.dto.file.library.FileLibraryResponse;
import org.example.deltawebfacade.dto.file.library.PathLibraryResponse;
import org.example.deltawebfacade.model.gallery.FileData;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
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
        fileResponseUponReceipt.setDownloadURL(getDownloadUrl(fileResponseUponReceipt, page));
        return fileResponseUponReceipt;
    }

    public List<FileResponseUponReceipt> convertFromModelList(PathParams pathParams, List<FileData> fileDataList) {
        List<FileResponseUponReceipt> fileResponseUponReceiptList  = simpleConvert(fileDataList, FileResponseUponReceipt.class);
        for (FileResponseUponReceipt fileResponseUponReceipt : fileResponseUponReceiptList) {
            String downloadUrl = getDownloadUrl(fileResponseUponReceipt, pathParams.getPage());
            fileResponseUponReceipt.setDownloadURL(downloadUrl);
        }
        return fileResponseUponReceiptList;
    }

    public PathParams convertFromPathRequest(PathRequest pathRequest, String page) {
        PathParams pathParams = new PathParams();
        pathParams.setAuthor(pathRequest.getAuthor() == null ? "Empty" : pathRequest.getAuthor());
        pathParams.setYear(pathRequest.getYear() == null ? 0 : pathRequest.getYear());
        pathParams.setName(pathRequest.getName());
        pathParams.setPath(pathRequest.getPath());
        pathParams.setPage(page);
        return pathParams;
    }

    public String getDownloadUrl(FileBaseDto fileBaseDto, String page) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/delta/path-pages/")
                .path(page)
                .path("/download/")
                .path(String.valueOf(fileBaseDto.getId()))
                .toUriString();
    }

}