package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.required.BackupRepository;
import com.sprint.hrbank.domain.backup.Backup;
import com.sprint.hrbank.domain.backup.BackupStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BackupQueryService {

  private final BackupRepository backupRepository;

  @Transactional(readOnly = true)
  public BackupDto getLatestBackupByStatus(BackupStatus status) {
    // 가장 최신상태의 백업 조회
    Backup target = backupRepository.findTopByStatusOrderByEndedAtDesc(status);
    return BackupDto.from(target);
  }
}
