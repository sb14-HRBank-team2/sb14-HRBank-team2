package com.sprint.hrbank.application.backup.dto;

import com.sprint.hrbank.domain.backup.Backup;
import com.sprint.hrbank.domain.backup.BackupStatus;
import java.time.LocalDateTime;

public record BackupDto(
    Long id,
    String worker,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    BackupStatus status,
    Long fileId) {

  public static BackupDto from(Backup backup) {
    Long target = null;
    if (backup.getStatus() == BackupStatus.COMPLETED || backup.getStatus() == BackupStatus.FAILED) {
      // 파일이 존재하니까 가져와도됌 아니면 NPE발생
      target = backup.getFileInfo().getId();
    }

    return new BackupDto(
        backup.getId(),
        backup.getWorker(),
        backup.getStartedAt(),
        backup.getEndedAt(),
        backup.getStatus(),
        target);
  }
}
