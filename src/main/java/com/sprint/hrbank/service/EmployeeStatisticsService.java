package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.DepartmentDistributionDto;
import com.sprint.hrbank.dto.MonthlyTrendDto;
import com.sprint.hrbank.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeStatisticsService {

    private final EmployeeRepository employeeRepository;

    public long getTotalCount() {
        return employeeRepository.count();
    }

    public List<MonthlyTrendDto> getMonthlyTrend() {
        return employeeRepository.findMonthlyTrend();
    }

    public List<DepartmentDistributionDto> getDepartmentDistribution() {
        return employeeRepository.findDepartmentDistribution();
    }
}