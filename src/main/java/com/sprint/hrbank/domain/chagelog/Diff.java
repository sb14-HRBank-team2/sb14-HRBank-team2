package com.sprint.hrbank.domain.chagelog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Diff {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(nullable = false)
  String propertyName;

  @Column(nullable = false)
  String before;

  @Column(nullable = false)
  String after;

  @ManyToOne
  @JoinColumn(name = "change_log_id", nullable = false)
  ChangeLog changeLog;

  private Diff(String propertyName, String before, String after, ChangeLog changeLog) {
    this.propertyName = propertyName;
    this.before = before;
    this.after = after;
    this.changeLog = changeLog;
  }

  public static Diff create(String propertyName, String before, String after, ChangeLog changeLog) {
    return new Diff(propertyName, before, after, changeLog);
  }
}
