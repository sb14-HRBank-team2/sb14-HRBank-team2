package com.sprint.hrbank.application.changelog.dto;

import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import com.sprint.hrbank.domain.employee.Employee;
import java.time.LocalDateTime;
import java.util.List;

public record ChangeLogDetailDto(
    Long id,
    ChangeType type,
    String employeeNumber,
    String memo,
    String ipAddress,
    LocalDateTime at,
    String employeeName,
    Long profileImageId,
    List<DiffResponseDto> diffs) {

  public static ChangeLogDetailDto from(
      ChangeLog changeLog, Employee employee, List<DiffResponseDto> diffs) {
    return new ChangeLogDetailDto(
        changeLog.getId(),
        changeLog.getType(),
        changeLog.getEmployeeNumber(),
        changeLog.getMemo(),
        changeLog.getIpAddress(),
        changeLog.getAt(),
        // NPE 방지
        employee == null ? null : employee.getName(),
        employee == null ? null : employee.getProfileImageId(),
        diffs);
  }
}
