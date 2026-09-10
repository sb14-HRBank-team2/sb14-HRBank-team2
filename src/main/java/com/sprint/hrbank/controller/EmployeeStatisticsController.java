package com.sprint.hrbank.controller;

import com.sprint.hrbank.dto.DepartmentDistributionDto;
import com.sprint.hrbank.dto.MonthlyTrendDto;
import com.sprint.hrbank.service.EmployeeStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeStatisticsController {

    private final EmployeeStatisticsService statisticsService;

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalEmployeeCount() {
        long count = statisticsService.getTotalCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/status/trend")
    public ResponseEntity<List<MonthlyTrendDto>> getEmployeeStatusTrend() {
        List<MonthlyTrendDto> trendList = statisticsService.getMonthlyTrend();
        return ResponseEntity.ok(trendList);
    }

    @GetMapping("/stats/distribution")
    public ResponseEntity<List<DepartmentDistributionDto>> getDepartmentDistribution() {
        List<DepartmentDistributionDto> distributionList = statisticsService.getDepartmentDistribution();
        return ResponseEntity.ok(distributionList);
    }
}