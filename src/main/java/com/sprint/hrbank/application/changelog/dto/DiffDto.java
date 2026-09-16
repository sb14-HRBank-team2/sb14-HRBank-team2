package com.sprint.hrbank.application.changelog.dto;

import com.sprint.hrbank.domain.chagelog.Diff;

public record DiffDto(String propertyName, String before, String after) {

  public static DiffDto from(Diff diff) {
    return new DiffDto(diff.getPropertyName(), diff.getBefore(), diff.getAfter());
  }
}
