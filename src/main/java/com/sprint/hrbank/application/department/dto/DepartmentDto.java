package com.sprint.hrbank.application.department.dto;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DepartmentDto(
    Integer id,
    String name,
    String description,
    LocalDate establishedDate,
    Integer employeeCount) {}
