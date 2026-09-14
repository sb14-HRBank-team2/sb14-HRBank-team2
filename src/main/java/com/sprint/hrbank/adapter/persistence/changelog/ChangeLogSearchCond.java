package com.sprint.hrbank.adapter.persistence.changelog;

import com.sprint.hrbank.domain.chagelog.ChangeType;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChangeLogSearchCond(
    String employeeNumber,
    ChangeType type,
    String memo,
    String ipAddress,
    LocalDateTime atFrom,
    LocalDateTime atTo,
    Long idAfter,
    String cursor,
    Integer size,
    String sortField,
    String sortDirection) {

  public ChangeLogSearchCond {
    if (size == null) {
      size = 10;
    }
    if (sortField == null) {
      sortField = "at";
    }
    if (sortDirection == null) {
      sortDirection = "desc";
    }
  }
}
