package com.sprint.hrbank.controller;

import com.sprint.hrbank.service.DownloadFileFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class DownloadFileController {
    private final DownloadFileFinder downloadFileFinder;

    // 파일 다운로드 버튼
    @RequestMapping(method = RequestMethod.GET, value = "/api/files/{id}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer id) throws MalformedURLException {
        Resource file = downloadFileFinder.downloadFile(id);

        String originalFileName = file.getFilename();

        String contentDisposition = ContentDisposition.builder("attachment")
                .filename(originalFileName, StandardCharsets.UTF_8)
                .build()
                .toString();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("text/csv; charset=MS949"))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(file);
    }
}

