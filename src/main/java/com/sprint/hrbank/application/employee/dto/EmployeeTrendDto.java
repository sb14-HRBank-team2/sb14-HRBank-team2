package com.sprint.hrbank.application.employee.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record EmployeeTrendDto(LocalDate date, Long count, Long change, Double changeRate) {}
