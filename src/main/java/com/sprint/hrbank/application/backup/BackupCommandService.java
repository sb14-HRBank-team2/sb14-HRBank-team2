package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.required.BackupRepository;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.backup.Backup;
import com.sprint.hrbank.domain.backup.BackupStatus;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BackupCommandService {

  private final BackupRepository backupRepository;
  private final ChangeLogRepository changeLogRepository;

  @Transactional
  public BackupDto create(String worker) {
    if (backupRepository.existsByStatus(BackupStatus.IN_PROGRESS)) {
      throw new CustomRuntimeException(ExceptionType.BACKUP_ING);
    }

    Backup backup = Backup.create(worker);
    /*
    가장 최근에 백업 완료된 객체 가져오기,
    그다음 객체에서 시간 뽑은 다음에 현재 시간사이에 변경이력 몇개있나 확인
     */
    Backup target =
        backupRepository.findTopByStatusOrderByEndedAtDesc(BackupStatus.COMPLETED).orElse(null);

    // 첫 백업은 완료된 이력이 없으므로 IN_PROGRESS로 저장
    if (target != null
        && changeLogRepository.countByAtBetween(target.getEndedAt(), LocalDateTime.now()) == 0) {
      // 0이면 변경이력 존재 x 백업할필요없
      backup.skip();
    }

    // 변경 이력이 있으면 IN_PROGRESS, 없으면 SKIPPED로 저장
    return BackupDto.from(backupRepository.save(backup));
  }
}
