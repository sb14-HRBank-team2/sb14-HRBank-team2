package com.sprint.hrbank.adapter.webapi.fileinfo;

import com.sprint.hrbank.application.fileinfo.FileInfoService;
import com.sprint.hrbank.application.fileinfo.dto.FileDownloadDto;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

  private final FileInfoService fileInfoService;

  @GetMapping("/{id}/download")
  public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
    FileDownloadDto downloadDto = fileInfoService.downloadFile(id);
    String encodedFileName = UriUtils.encode(downloadDto.fileName(), StandardCharsets.UTF_8);

    String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .contentType(MediaType.parseMediaType(downloadDto.contentType()))
        .body(downloadDto.resource());
  }
}
