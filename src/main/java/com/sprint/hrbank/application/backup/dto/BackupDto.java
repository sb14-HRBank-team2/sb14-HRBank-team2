package com.sprint.hrbank.application.backup.dto;

import com.sprint.hrbank.domain.backup.BackupStatus;
import java.time.LocalDateTime;

public record BackupDto(
    Long id,
    String worker,
    LocalDateTime startedAt,
    LocalDateTime endedAt,
    BackupStatus status,
    Long fileId) {}
