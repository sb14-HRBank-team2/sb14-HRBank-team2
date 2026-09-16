package com.sprint.hrbank.application.changelog.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record CursorPageResponseChangeLogDto(
    List<ChangeLogDto> content,
    String nextCursor,
    Long nextIdAfter,
    int size,
    Long totalElements,
    boolean hasNext) {}
