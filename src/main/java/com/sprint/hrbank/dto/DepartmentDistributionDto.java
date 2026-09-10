package com.sprint.hrbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DepartmentDistributionDto {
    private String departmentName; // 부서명
    private long employeeCount;    // 직원 수
}