package com.sprint.hrbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDistributionDto {
    private String departmentName;
    private long employeeCount;
}