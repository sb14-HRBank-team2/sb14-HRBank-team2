package com.sprint.hrbank.dto;

import com.sprint.hrbank.entity.Department;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record DepartmentDto(
    Integer id, String name, String description, LocalDate establishedDate, Integer employeeCount) {

  public static DepartmentDto from(Department department, Integer employeeCount) {
    return DepartmentDto.builder()
        .id(department.getId())
        .name(department.getName())
        .description(department.getDescription())
        .establishedDate(department.getEstablishedDate())
        .employeeCount(employeeCount)
        .build();
  }
}
