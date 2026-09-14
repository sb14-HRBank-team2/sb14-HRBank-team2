package com.sprint.hrbank.domain.backup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum BackupStatus {
  IN_PROGRESS("진행중"),
  COMPLETED("완료"),
  SKIPPED("건너뜀"),
  FAILED("실패");

  String description;
}
