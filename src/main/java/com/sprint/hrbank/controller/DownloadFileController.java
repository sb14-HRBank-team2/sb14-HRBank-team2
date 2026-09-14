package com.sprint.hrbank.controller;

import com.sprint.hrbank.service.DownloadFileFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class DownloadFileController {
    private final DownloadFileFinder downloadFileFinder;

    // 파일 다운로드 버튼
    @RequestMapping(method = RequestMethod.GET, value = "/api/files/{id}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) throws MalformedURLException {
        Resource resource = downloadFileFinder.downloadFile(id);

        String originalFileName = resource.getFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            originalFileName = "test.csv";
        }
        
        String encodedFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}

