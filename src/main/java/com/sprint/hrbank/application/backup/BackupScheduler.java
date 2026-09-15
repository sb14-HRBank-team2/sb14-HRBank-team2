package com.sprint.hrbank.application.backup;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackupScheduler {

  private final BackupCommandService backupCommandService;

  @Scheduled(fixedDelayString = "${backup.schedule.fixed-delay}", timeUnit = TimeUnit.SECONDS)
  public void createBackup() {
    backupCommandService.create("system");
  }
}
