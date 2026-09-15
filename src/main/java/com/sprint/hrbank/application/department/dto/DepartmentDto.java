package com.sprint.hrbank.application.department.dto;

import com.sprint.hrbank.domain.department.Department;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DepartmentDto(
    Long id, String name, String description, LocalDate establishedDate, Long employeeCount) {

  public static DepartmentDto from(Department department, Long employeeCount) {
    return DepartmentDto.builder()
        .id(department.getId())
        .name(department.getName())
        .description(department.getDescription())
        .employeeCount(employeeCount)
        .establishedDate(department.getEstablishedDate())
        .build();
  }
}
