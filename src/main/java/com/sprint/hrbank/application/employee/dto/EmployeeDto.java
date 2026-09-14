package com.sprint.hrbank.application.employee.dto;

import com.sprint.hrbank.domain.employee.Employee;
import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record EmployeeDto(
    Long id,
    String name,
    String email,
    String employeeNumber,
    Long departmentId,
    String departmentName,
    String position,
    LocalDate hireDate,
    EmployeeStatus status,
    Long profileImageId) {

  public static EmployeeDto from(Employee employee) {
    return EmployeeDto.builder()
        .id(employee.getId())
        .name(employee.getName())
        .email(employee.getEmail())
        .employeeNumber(employee.getEmployeeNumber())
        .departmentId(employee.getDepartment().getId())
        .departmentName(employee.getDepartment().getName())
        .position(employee.getPosition())
        .hireDate(employee.getHireDate())
        .status(employee.getStatus())
        .profileImageId(employee.getProfileImageId())
        .build();
  }
}
