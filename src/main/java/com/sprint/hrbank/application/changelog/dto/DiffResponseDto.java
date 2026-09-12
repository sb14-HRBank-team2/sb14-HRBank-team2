package com.sprint.hrbank.application.changelog.dto;

import com.sprint.hrbank.domain.chagelog.Diff;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiffResponseDto {

  String propertyName;
  String before;
  String after;

  public static DiffResponseDto from(Diff diff) {
    return new DiffResponseDto(diff.getPropertyName(), diff.getBefore(), diff.getAfter());
  }
}
