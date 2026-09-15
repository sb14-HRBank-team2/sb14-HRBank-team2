package com.sprint.hrbank.adapter.persistence.backup;

import com.sprint.hrbank.domain.backup.BackupStatus;
import java.time.LocalDate;

public record BackupSearchCond(
    String worker,
    BackupStatus status,
    LocalDate searchDate,
    Long idAfter,
    String cursor,
    Integer size,
    String sortField,
    String sortDirection) {

  public BackupSearchCond {
    if (size == null) {
      size = 10;
    }
    if (sortField == null) {
      sortField = "startedAt";
    }
    if (sortDirection == null) {
      sortDirection = "desc";
    }
  }
}
