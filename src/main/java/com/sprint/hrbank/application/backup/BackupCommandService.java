package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.required.BackupRepository;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.domain.backup.Backup;
import com.sprint.hrbank.domain.backup.BackupStatus;
import java.time.LocalDateTime;
import java.util.Objects;
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
    Backup backup = Backup.create(worker);
    /*
    가장 최근에 백업 완료된 객체 가져오기,
    그다음 객체에서 시간 뽑은 다음에 현재 시간사이에 변경이력 몇개있나 확인
     */
    Backup target = backupRepository.findTopByStatusOrderByEndedAtDesc(BackupStatus.COMPLETED);
    // 첫백업 NPE 방지
    if (Objects.isNull(target)) {
      return BackupDto.from(backupRepository.save(backup));
    }
    Long isChanged = changeLogRepository.countByAtBetween(target.getEndedAt(), LocalDateTime.now());
    // 0이면 변경이력 존재 x 백업할필요없 그이상 자연수면 변경이력존재
    if (isChanged == 0) {
      backup.skip();
    }
    // 그게 아니면 스킵없이 저장
    return BackupDto.from(backupRepository.save(backup));
  }
}
