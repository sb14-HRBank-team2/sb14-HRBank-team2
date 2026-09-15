package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.required.BackupRepository;
import com.sprint.hrbank.application.changelog.required.ChangeLogRepository;
import com.sprint.hrbank.application.fileinfo.FileInfoService;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.backup.Backup;
import com.sprint.hrbank.domain.backup.BackupStatus;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BackupCommandService {

  private final BackupRepository backupRepository;
  private final ChangeLogRepository changeLogRepository;
  private final CSVService csvService;
  private final LogService logService;
  private final FileInfoService fileInfoService;

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
    // 백업이력 저장한 엔티티 반환
    Backup gift = backupRepository.save(backup);
    // 중요: 건너뜀 이면 CSV파일생성 없음
    if (gift.getStatus() == BackupStatus.SKIPPED) {
      return BackupDto.from(gift);
    }
    // 건너뜀이 아니면 직원정보 리스트로 CSV로 뽑고 파일 메타데이터 저장
    Path csvPath = null;
    try {
      // CSV 파일 생성
      csvPath = csvService.createCSV();
      gift.complete(fileInfoService.register(csvPath));
    } catch (RuntimeException exception) {
      // CSV fileInfo연동 실패시
      if (csvPath != null) {
        try { // 생성된 CSV삭제
          Files.deleteIfExists(csvPath);
        } catch (IOException ignored) {
        }
      }
      // 그리고 실패.log 파일생성
      gift.fail(fileInfoService.register(logService.createLog(worker, exception)));
    }
    return BackupDto.from(gift);
  }
}
