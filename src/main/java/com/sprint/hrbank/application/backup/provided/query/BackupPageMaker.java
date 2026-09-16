package com.sprint.hrbank.application.backup.provided.query;

import com.sprint.hrbank.adapter.persistence.backup.BackupSearchCond;
import com.sprint.hrbank.application.backup.dto.CursorPageResponseBackupDto;

public interface BackupPageMaker {

  CursorPageResponseBackupDto getBackups(BackupSearchCond cond);
}
