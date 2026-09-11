package com.sprint.hrbank.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sprint.hrbank.entity.Department;
import lombok.Getter;

import java.time.LocalDate;

// 부서 정보
@Getter
public class DepartmentDto {
  private final Integer id; // 부서 ID
  private final String name; // 부서 이름
  private final String description; // 부서 설명
  private final Integer employeeCount; // 소속 직원 수

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private final LocalDate establishedDate; // 부서 설립일

  private DepartmentDto(Department department) {
    this.id = department.getId();
    this.name = department.getName();
    this.description = department.getDescription();
    this.employeeCount = department.getEmployees().size();
    this.establishedDate = department.getEstablishedDate().toLocalDate();
  }

  public static DepartmentDto from(Department entity) {
    return new DepartmentDto(entity);
  }
}