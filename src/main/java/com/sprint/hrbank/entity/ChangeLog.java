package com.sprint.hrbank.entity;

import com.sprint.hrbank.ChangeType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  ChangeType type;

  @Column(nullable = false)
  String employeeNumber;

  String memo;

  @Column(nullable = false)
  String ipAddress;

  @Column(nullable = false)
  LocalDateTime at;

  private ChangeLog(ChangeType type, String employeeNumber, String memo, String ipAddress) {
    this.type = type;
    this.employeeNumber = employeeNumber;
    this.memo = memo;
    this.ipAddress = ipAddress;
    this.at = LocalDateTime.now();
  }

  public static ChangeLog create(
      ChangeType type, String employeeNumber, String memo, String ipAddress) {
    return new ChangeLog(type, employeeNumber, memo, ipAddress);
  }
}
