package com.sprint.hrbank.application.backup;

import com.sprint.hrbank.application.backup.provided.command.BackupCreator;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackupScheduler {

  private final BackupCreator backupCreator;

  @Scheduled(fixedDelayString = "${backup.schedule.fixed-delay}", timeUnit = TimeUnit.SECONDS)
  public void createBackup() {
    backupCreator.create("system");
  }
}
