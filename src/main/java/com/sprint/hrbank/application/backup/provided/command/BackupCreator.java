package com.sprint.hrbank.application.backup.provided.command;

import com.sprint.hrbank.application.backup.dto.BackupDto;
import org.springframework.transaction.annotation.Transactional;

public interface BackupCreator {

  @Transactional
  BackupDto create(String worker);
}
