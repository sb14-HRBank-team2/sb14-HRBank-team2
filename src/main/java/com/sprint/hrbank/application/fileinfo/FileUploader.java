package com.sprint.hrbank.application.fileinfo;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploader {

  @Transactional
  Long uploadFile(MultipartFile file);
}
