package com.sprint.hrbank.domain.backup;

import com.sprint.hrbank.domain.fileinfo.FileInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Backup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(nullable = false)
  String worker;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  BackupStatus status;

  @Column(nullable = false)
  LocalDateTime startedAt;

  LocalDateTime endedAt;

  @OneToOne
  @JoinColumn(name = "file_id")
  FileInfo fileInfo;

  private Backup(String worker, BackupStatus status) {
    this.worker = worker;
    this.status = status;
    this.startedAt = LocalDateTime.now();
  }

  public static Backup create(String worker) {
    return new Backup(worker, BackupStatus.IN_PROGRESS);
  }

  public void complete(FileInfo fileInfo) {
    this.fileInfo = fileInfo;
    this.status = BackupStatus.COMPLETED;
    this.endedAt = LocalDateTime.now();
  }

  public void skip() {
    this.status = BackupStatus.SKIPPED;
    this.endedAt = LocalDateTime.now();
  }

  public void fail(FileInfo fileInfo) {
    this.fileInfo = fileInfo;
    this.status = BackupStatus.FAILED;
    this.endedAt = LocalDateTime.now();
  }
}
