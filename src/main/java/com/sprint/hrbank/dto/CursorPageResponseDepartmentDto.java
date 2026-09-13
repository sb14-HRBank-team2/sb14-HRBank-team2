package com.sprint.hrbank.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record CursorPageResponseDepartmentDto(
    List<DepartmentDto> content,
    String nextCursor,
    Long nextIdAfter,
    Integer size,
    Long totalElements,
    Boolean hasNext) {

  public static CursorPageResponseDepartmentDto of(
      List<DepartmentDto> content,
      Integer size,
      Long totalElements,
      Boolean hasNext,
      Long nextIdAfter) {
    return CursorPageResponseDepartmentDto.builder()
        .content(content)
        .size(size)
        .totalElements(totalElements)
        .hasNext(hasNext)
        .nextIdAfter(nextIdAfter)
        // nextCursor를 사용자로부터 받지 않고 내부 로직으로 처리.
        .nextCursor(nextIdAfter != null ? String.valueOf(nextIdAfter) : null)
        .build();
  }
}
