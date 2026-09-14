package com.sprint.hrbank.adapter.webapi.backup;

import com.sprint.hrbank.application.backup.BackupQueryService;
import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.domain.backup.BackupStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/backups")
public class BackupController {

  private final BackupQueryService backupQueryService;

  @GetMapping("/latest")
  public BackupDto getLatestBackup(@RequestParam(defaultValue = "COMPLETED") BackupStatus status) {
    return backupQueryService.getLatestBackupByStatus(status);
  }
}
