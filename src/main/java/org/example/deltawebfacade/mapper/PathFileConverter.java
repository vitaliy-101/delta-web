package org.example.deltawebfacade.mapper;

import lombok.RequiredArgsConstructor;
import org.example.deltawebfacade.dto.file.*;
import org.example.deltawebfacade.dto.path.PathParams;
import org.example.deltawebfacade.dto.path.PathRequest;
import org.example.deltawebfacade.dto.path.knowledge.KnowledgeBaseResponse;
import org.example.deltawebfacade.model.file.FileData;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PathFileConverter extends DtoConverter {

    public List<FileResponseUponReceipt> convertFromModelList(FileParams fileParams, List<FileData> fileDataList) {
        List<FileResponseUponReceipt> fileResponseUponReceiptList  = simpleConvert(fileDataList, FileResponseUponReceipt.class);
        for (FileResponseUponReceipt fileResponseUponReceipt : fileResponseUponReceiptList) {
            String downloadUrl = getDownloadUrl(fileResponseUponReceipt, fileParams.getPage());
            fileResponseUponReceipt.setDownloadURL(downloadUrl);
        }
        return fileResponseUponReceiptList;
    }

    public PathParams convertFromPathRequest(PathRequest pathRequest, String page) {
        FileParams fileParams = convertFromFileRequest(pathRequest, page);
        PathParams pathParams = simpleConvert(fileParams, PathParams.class);
        pathParams.setName(pathRequest.getName());
        pathParams.setIsBase(pathRequest.getIsBase());
        return pathParams;
    }

    public FileParams convertFromFileRequest(FileRequest fileRequest, String page) {
        FileParams fileParams = new FileParams();
        fileParams.setAuthor(fileRequest.getAuthor() == null ? "Empty" : fileRequest.getAuthor());
        fileParams.setYear(fileRequest.getYear() == null ? 0 : fileRequest.getYear());
        fileParams.setPath(fileRequest.getPath());
        fileParams.setPage(page);
        return fileParams;
    }

    public List<KnowledgeBaseResponse> convertFromModelListToKnowledgeResponse(List<FileData> fileDataList) {
        return fileDataList.stream().map(this::convertFromModelToKnowledgeResponse).toList();
    }

    public KnowledgeBaseResponse convertFromModelToKnowledgeResponse(FileData fileData) {
        KnowledgeBaseResponse knowledgeBaseResponse = new KnowledgeBaseResponse();
        String page = fileData.getPath().split("/")[0];
        knowledgeBaseResponse.setName(fileData.getName());
        knowledgeBaseResponse.setPage(page);
        knowledgeBaseResponse.setImageUrl(getDownloadUrl(simpleConvert(fileData, FileBaseResponseDto.class), page));
        knowledgeBaseResponse.setFolderId(fileData.getId());
        return knowledgeBaseResponse;
    }



    public String getDownloadUrl(FileBaseResponseDto fileBaseDto, String page) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/delta/path-pages/")
                .path("download/")
                .path(String.valueOf(fileBaseDto.getId()))
                .toUriString();
    }

}