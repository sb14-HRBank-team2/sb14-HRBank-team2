package com.sprint.hrbank.dto;

import java.time.LocalDate;

public record EmployeeCreateRequest(
    String name,
    String email,
    Integer departmentId,
    String position,
    LocalDate hireDate,
    String memo) {}
