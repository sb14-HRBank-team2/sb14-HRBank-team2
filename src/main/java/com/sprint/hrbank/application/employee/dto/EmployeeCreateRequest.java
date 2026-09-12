package com.sprint.hrbank.application.employee.dto;

import java.time.LocalDate;

public record EmployeeCreateRequest(
    String name,
    String email,
    Integer departmentId,
    String position,
    LocalDate hireDate,
    String memo) {}
