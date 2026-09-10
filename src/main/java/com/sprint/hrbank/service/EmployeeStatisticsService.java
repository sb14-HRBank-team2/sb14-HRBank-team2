package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.DepartmentDistributionDto;
import com.sprint.hrbank.dto.EmployeeStatisticsResponse;
import com.sprint.hrbank.dto.MonthlyTrendDto;
import com.sprint.hrbank.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 통계는 읽기 전용이므로 성능을 높여줍니다!
public class EmployeeStatisticsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeStatisticsResponse getEmployeeStatistics() {
        // 1. 총 재직자 수 구하기 (기준 상태: "재직중")
        long totalCount = employeeRepository.countByStatus("재직중");

        // 2. 부서별 분포 데이터를 꺼내서 DTO 그릇에 옮겨 담기
        List<DepartmentDistributionDto> deptDistribution = employeeRepository.countEmployeesByDepartment()
                .stream()
                .map(stats -> new DepartmentDistributionDto(stats.getDepartmentName(), stats.getEmployeeCount()))
                .collect(Collectors.toList());

        // 3. 월별 입사자 추이 데이터를 꺼내서 DTO 그릇에 옮겨 담기
        List<MonthlyTrendDto> monthlyTrend = employeeRepository.countJoinTrend()
                .stream()
                .map(stats -> new MonthlyTrendDto(stats.getYearMonth(), stats.getJoinCount()))
                .collect(Collectors.toList());

        // 4. 모든 데이터를 최종 응답 DTO 상자에 예쁘게 포장해서 반환
        return EmployeeStatisticsResponse.builder()
                .totalEmployees(totalCount)
                .departmentDistribution(deptDistribution)
                .monthlyTrend(monthlyTrend)
                .build();
    }
}