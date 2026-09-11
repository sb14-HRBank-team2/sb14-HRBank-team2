package com.sprint.hrbank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ChangeType {
  CREATE("생성"),
  UPDATED("수정"),
  DELETED("삭제");

  String description;
}
