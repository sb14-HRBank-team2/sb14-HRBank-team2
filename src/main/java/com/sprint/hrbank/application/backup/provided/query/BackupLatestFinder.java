package com.sprint.hrbank.application.backup.provided.query;

import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.domain.backup.BackupStatus;

public interface BackupLatestFinder {

  BackupDto getLatestBackupByStatus(BackupStatus status);
}
