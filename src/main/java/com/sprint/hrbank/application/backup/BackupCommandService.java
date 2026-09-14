package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.required.BackupRepository;
import com.sprint.hrbank.domain.backup.Backup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BackupCommandService {

  private final BackupRepository backupRepository;

  @Transactional
  public BackupDto create(String worker) {
    Backup backup = Backup.create(worker);
    return BackupDto.from(backupRepository.save(backup));
  }
}
