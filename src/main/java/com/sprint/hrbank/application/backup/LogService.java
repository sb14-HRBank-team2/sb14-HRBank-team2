package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LogService {

  @Value("${file.dir:/tmp/hrbank/files/}")
  private String fileDirectory;

  public Path createLog(String worker, Exception exception) {
    String fileName = "backup_failed" + LocalDateTime.now().toString().replace(":", "-") + ".log";
    Path path = Paths.get(fileDirectory, fileName);

    try {
      Files.createDirectories(path.getParent());

      try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
        writer.write(LocalDateTime.now() + ": 백업실패");
        writer.newLine();
        writer.write("worker: " + worker);
        writer.newLine();
        writer.write("실패원인: " + exception.getMessage());
      }
    } catch (IOException e) {
      throw new CustomRuntimeException(ExceptionType.BACKUP_FILE_EXEPTION);
    }

    return path;
  }
}
