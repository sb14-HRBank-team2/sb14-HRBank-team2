package com.sprint.hrbank.application.fileinfo;

import com.sprint.hrbank.domain.fileinfo.FileInfo;
import java.nio.file.Path;
import org.springframework.transaction.annotation.Transactional;

public interface FileCreater {

  @Transactional
  FileInfo register(Path path);
}
