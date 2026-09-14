package com.sprint.hrbank.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record DepartmentDto(
    Integer id,
    String name,
    String description,
    LocalDate establishedDate,
    Integer employeeCount) {}
