package com.sprint.hrbank.application.employee.dto;

import lombok.Builder;

@Builder
public record EmployeeDistributionDto(String groupKey, Long count, Double percentage) {}
