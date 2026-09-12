package com.sprint.hrbank.application.employee.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record CursorPageResponseEmployeeDto(
    List<EmployeeDto> content,
    String nextCursor,
    Long nextIdAfter,
    int size,
    Long totalElements,
    boolean hasNext) {}
