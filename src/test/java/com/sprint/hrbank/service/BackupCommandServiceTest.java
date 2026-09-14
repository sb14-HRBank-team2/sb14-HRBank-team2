package com.sprint.hrbank.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sprint.hrbank.application.backup.BackupCommandService;
import com.sprint.hrbank.application.backup.dto.BackupDto;
import com.sprint.hrbank.application.backup.required.BackupRepository;
import com.sprint.hrbank.common.config.QuerydslConfig;
import com.sprint.hrbank.domain.backup.Backup;
import com.sprint.hrbank.domain.backup.BackupStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(
    properties = {
      "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
      "spring.jpa.hibernate.ddl-auto=create-drop",
      "spring.data.jpa.repositories.bootstrap-mode=lazy"
    })
@Import({BackupCommandService.class, QuerydslConfig.class})
@Transactional
class BackupCommandServiceTest {

  @Autowired private BackupCommandService backupCommandService;

  @Autowired private BackupRepository backupRepository;

  @Test
  @DisplayName("백업 이력을 저장한다")
  void createBackup() {
    BackupDto result = backupCommandService.create("ip주소");

    Backup saved = backupRepository.findById(result.id()).orElseThrow();

    assertThat(result.worker()).isEqualTo("ip주소");
    assertThat(result.status()).isEqualTo(BackupStatus.IN_PROGRESS);
    assertThat(result.startedAt()).isNotNull();
    assertThat(result.endedAt()).isNull();
    assertThat(result.fileId()).isNull();
    assertThat(saved.getStatus()).isEqualTo(BackupStatus.IN_PROGRESS);
    assertThat(saved.getWorker()).isEqualTo("ip주소");
  }
}
