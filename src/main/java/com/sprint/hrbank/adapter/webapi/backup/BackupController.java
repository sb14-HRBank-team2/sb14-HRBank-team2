package com.sprint.hrbank.adapter.webapi.backup;

import com.sprint.hrbank.adapter.persistence.backup.BackupSearchCond;
import com.sprint.hrbank.application.backup.BackupCommandService;
import com.sprint.hrbank.application.backup.BackupQueryService;
import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.dto.CursorPageResponseBackupDto;
import com.sprint.hrbank.domain.backup.BackupStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/backups")
public class BackupController {

  private final BackupCommandService backupCommandService;
  private final BackupQueryService backupQueryService;

  @PostMapping
  public BackupDto createBackup(HttpServletRequest request) {
    return backupCommandService.create(request.getRemoteAddr());
  }

  @GetMapping("/latest")
  public BackupDto getLatestBackup(@RequestParam(defaultValue = "COMPLETED") BackupStatus status) {
    return backupQueryService.getLatestBackupByStatus(status);
  }

  @GetMapping
  public ResponseEntity<CursorPageResponseBackupDto> getBackups(
      @ModelAttribute BackupSearchCond cond) {
    CursorPageResponseBackupDto response = backupQueryService.getBackups(cond);
    return ResponseEntity.ok(response);
  }
}
