package com.sprint.hrbank.dto;

import com.sprint.hrbank.entity.ChangeLog;
import com.sprint.hrbank.entity.Diff;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiffCreateRequestDto {

  String propertyName;
  String before;
  String after;

  public Diff toEntity(ChangeLog changeLog) {
    return Diff.create(this.propertyName, this.before, this.after, changeLog);
  }
}
