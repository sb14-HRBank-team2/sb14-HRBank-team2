package com.sprint.hrbank.application.changelog.dto;

import com.sprint.hrbank.domain.chagelog.ChangeLog;
import com.sprint.hrbank.domain.chagelog.ChangeType;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeLogCreateRequestDto {

  ChangeType type;
  String employeeNumber;
  String memo;
  List<DiffCreateRequestDto> diffs;

  public ChangeLog toEntity(String ipAddress) {
    return ChangeLog.create(type, employeeNumber, memo, ipAddress);
  }
}
