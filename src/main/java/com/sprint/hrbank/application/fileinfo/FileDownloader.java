package com.sprint.hrbank.application.fileinfo;

import com.sprint.hrbank.application.fileinfo.dto.FileDownloadDto;
import org.springframework.transaction.annotation.Transactional;

public interface FileDownloader {

  @Transactional(readOnly = true)
  FileDownloadDto downloadFile(Long fileId);
}
