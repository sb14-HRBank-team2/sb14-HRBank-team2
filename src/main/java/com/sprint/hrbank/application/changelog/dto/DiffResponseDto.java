package com.sprint.hrbank.application.changelog.dto;

import com.sprint.hrbank.domain.chagelog.Diff;

public record DiffResponseDto(String propertyName, String before, String after) {

  public static DiffResponseDto from(Diff diff) {
    return new DiffResponseDto(diff.getPropertyName(), diff.getBefore(), diff.getAfter());
  }
}
