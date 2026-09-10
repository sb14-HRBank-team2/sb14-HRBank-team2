package com.sprint.hrbank.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class EmployeeStatisticsResponse {
    // 1. 현재 재직 중인 총 직원 수
    private long totalEmployees;

    // 2. 부서별 직원 수 분포
    private List<DepartmentDistributionDto> departmentDistribution;

    // 3. 월별 입사자 추이
    private List<MonthlyTrendDto> monthlyTrend;
}