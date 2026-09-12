package com.sprint.hrbank.dto;

import com.sprint.hrbank.ChangeType;
import com.sprint.hrbank.entity.ChangeLog;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
