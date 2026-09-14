package com.sprint.hrbank.dto;

import com.sprint.hrbank.entity.EmployeeStatus;
import java.time.LocalDate;

public record EmployeeUpdateRequest(
    String name,
    String email,
    Integer departmentId,
    String position,
    LocalDate hireDate,
    EmployeeStatus status,
    String memo) {}
