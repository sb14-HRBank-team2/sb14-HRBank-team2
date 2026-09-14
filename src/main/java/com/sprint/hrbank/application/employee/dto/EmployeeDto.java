package com.sprint.hrbank.application.employee.dto;

import com.sprint.hrbank.domain.employee.Employee;
import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.LocalDate;

public record EmployeeDto(
    Integer id,
    String name,
    String email,
    String employeeNumber,
    Integer departmentId,
    String departmentName,
    String position,
    LocalDate hireDate,
    EmployeeStatus status,
    Integer profileImageId) {

  public static EmployeeDto toDto(Employee employee) {
    return new EmployeeDto(
        employee.getId(),
        employee.getName(),
        employee.getEmail(),
        employee.getEmployeeNumber(),
        employee.getDepartment().getId(),
        employee.getDepartment().getName(),
        employee.getPosition(),
        employee.getHireDate(),
        employee.getStatus(),
        employee.getProfileImageId());
  }
}
