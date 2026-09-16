package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.adapter.persistence.backup.BackupSearchCond;
import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.dto.CursorPageResponseBackupDto;
import com.sprint.hrbank.application.backup.provided.query.BackupLatestFinder;
import com.sprint.hrbank.application.backup.provided.query.BackupPageMaker;
import com.sprint.hrbank.application.backup.required.BackupRepository;
import com.sprint.hrbank.common.exception.CustomRuntimeException;
import com.sprint.hrbank.common.exception.ExceptionType;
import com.sprint.hrbank.domain.backup.Backup;
import com.sprint.hrbank.domain.backup.BackupStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BackupQueryService implements BackupPageMaker, BackupLatestFinder {

  private final BackupRepository backupRepository;

  @Override
  @Transactional(readOnly = true)
  public BackupDto getLatestBackupByStatus(BackupStatus status) {
    Backup target =
        backupRepository
            .findTopByStatusOrderByEndedAtDesc(status)
            .orElseThrow(() -> new CustomRuntimeException(ExceptionType.BACKUP_NOT_FOUND));
    return BackupDto.from(target);
  }

  @Override
  @Transactional(readOnly = true)
  public CursorPageResponseBackupDto getBackups(BackupSearchCond cond) {
    List<Backup> searched = backupRepository.search(cond);

    Integer size = cond.size();
    boolean hasNext = searched.size() > size;
    List<Backup> paged;

    if (hasNext) {
      paged = searched.subList(0, size);
    } else {
      paged = searched;
    }

    List<BackupDto> pagedDto = paged.stream().map(BackupDto::from).toList();

    String nextCursor = null;
    Long nextIdAfter = null;
    Long totalElements = backupRepository.countByCondition(cond);

    if (hasNext && !paged.isEmpty()) {
      Backup lastBackup = paged.get(paged.size() - 1);
      nextCursor = getCursor(lastBackup, cond.sortField());
      nextIdAfter = lastBackup.getId();
    }

    return CursorPageResponseBackupDto.builder()
        .content(pagedDto)
        .nextCursor(nextCursor)
        .nextIdAfter(nextIdAfter)
        .size(size)
        .totalElements(totalElements)
        .hasNext(hasNext)
        .build();
  }

  private String getCursor(Backup backup, String sortField) {
    if ("startedAt".equals(sortField)) {
      return backup.getStartedAt() != null ? backup.getStartedAt().toString() : null;
    }
    if ("endedAt".equals(sortField)) {
      return backup.getEndedAt() != null ? backup.getEndedAt().toString() : null;
    }
    throw new CustomRuntimeException(ExceptionType.INVALID_REQUEST);
  }
}
