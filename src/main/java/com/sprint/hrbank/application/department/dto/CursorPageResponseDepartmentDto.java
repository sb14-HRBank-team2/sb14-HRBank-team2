package com.sprint.hrbank.application.department.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record CursorPageResponseDepartmentDto(
    List<DepartmentDto> content,
    String nextCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    Boolean hasNext) {}
