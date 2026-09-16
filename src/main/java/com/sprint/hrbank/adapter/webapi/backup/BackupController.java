package com.sprint.hrbank.adapter.webapi.backup;

import com.sprint.hrbank.adapter.persistence.backup.BackupSearchCond;
import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.dto.CursorPageResponseBackupDto;
import com.sprint.hrbank.application.backup.provided.command.BackupCreator;
import com.sprint.hrbank.application.backup.provided.query.BackupLatestFinder;
import com.sprint.hrbank.application.backup.provided.query.BackupPageMaker;
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

  private final BackupCreator creator;
  private final BackupLatestFinder finder;
  private final BackupPageMaker maker;

  @PostMapping
  public BackupDto createBackup(HttpServletRequest request) {
    return creator.create(request.getRemoteAddr());
  }

  @GetMapping("/latest")
  public BackupDto getLatestBackup(@RequestParam(defaultValue = "COMPLETED") BackupStatus status) {
    return finder.getLatestBackupByStatus(status);
  }

  @GetMapping
  public ResponseEntity<CursorPageResponseBackupDto> getBackups(
      @ModelAttribute BackupSearchCond cond) {
    CursorPageResponseBackupDto response = maker.getBackups(cond);
    return ResponseEntity.ok(response);
  }
}
