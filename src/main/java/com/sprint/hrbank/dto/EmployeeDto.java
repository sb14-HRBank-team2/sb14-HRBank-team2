package com.sprint.hrbank.dto;

import com.sprint.hrbank.domain.Employee;
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
    String status,
    Integer profileImageId) {
  public static EmployeeDto from(Employee employee) {
    return new EmployeeDto(
        employee.getId(),
        employee.getName(),
        employee.getEmail(),
        employee.getEmployeeNumber(),
        employee.getDepartment().getId(),
        employee.getDepartment().getName(),
        employee.getPosition(),
        employee.getHireDate(),
        employee.getStatus().name(),
        employee.getProfileImageId());
  }
}
