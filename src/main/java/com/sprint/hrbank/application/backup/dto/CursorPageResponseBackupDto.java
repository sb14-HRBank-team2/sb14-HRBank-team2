package com.sprint.hrbank.application.backup.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record CursorPageResponseBackupDto(
    List<BackupDto> content,
    String nextCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    Boolean hasNext) {}
