package com.sprint.hrbank.controller;

import com.sprint.hrbank.dto.EmployeeStatisticsResponse;
import com.sprint.hrbank.service.EmployeeStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class EmployeeStatisticsController {

    private final EmployeeStatisticsService statisticsService;

    // 통계 데이터 조회 API 엔드포인트
    @GetMapping("/employees")
    public ResponseEntity<EmployeeStatisticsResponse> getEmployeeStatistics() {
        EmployeeStatisticsResponse response = statisticsService.getEmployeeStatistics();
        return ResponseEntity.ok(response);
    }
}