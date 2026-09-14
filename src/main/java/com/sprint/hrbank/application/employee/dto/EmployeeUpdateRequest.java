package com.sprint.hrbank.application.employee.dto;

import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.LocalDate;

public record EmployeeUpdateRequest(
    String name,
    String email,
    Integer departmentId,
    String position,
    LocalDate hireDate,
    EmployeeStatus status,
    String memo) {}
