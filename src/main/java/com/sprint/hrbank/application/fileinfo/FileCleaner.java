package com.sprint.hrbank.application.fileinfo;

import org.springframework.transaction.annotation.Transactional;

public interface FileCleaner {

  @Transactional
  void deleteFile(Long fileId);
}
