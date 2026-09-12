package com.sprint.hrbank.application.changelog.dto;

import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeLogResponseDto {

  Integer id;
  ChangeType type;
  String employeeNumber;
  String memo;
  String ipAddress;
  LocalDateTime at;

  public static ChangeLogResponseDto from(ChangeLog changeLog) {
    return new ChangeLogResponseDto(
        changeLog.getId(),
        changeLog.getType(),
        changeLog.getEmployeeNumber(),
        changeLog.getMemo(),
        changeLog.getIpAddress(),
        changeLog.getAt());
  }
}
