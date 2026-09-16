package com.sprint.hrbank.application.changelog.dto;

import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import java.time.LocalDateTime;

public record ChangeLogDto(
    Long id,
    ChangeType type,
    String employeeNumber,
    String memo,
    String ipAddress,
    LocalDateTime at) {

  public static ChangeLogDto from(ChangeLog changeLog) {
    return new ChangeLogDto(
        changeLog.getId(),
        changeLog.getType(),
        changeLog.getEmployeeNumber(),
        changeLog.getMemo(),
        changeLog.getIpAddress(),
        changeLog.getAt());
  }
}
