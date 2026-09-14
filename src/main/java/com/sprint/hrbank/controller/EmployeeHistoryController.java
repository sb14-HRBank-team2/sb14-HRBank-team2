package com.sprint.hrbank.controller;

import com.sprint.hrbank.dto.EmployeeHistoryDto;
import com.sprint.hrbank.service.EmployeeHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeHistoryController {

    private final EmployeeHistoryService employeeHistoryService;

    @GetMapping("/{employeeId}/histories")
    public ResponseEntity<List<EmployeeHistoryDto>> getHistories(@PathVariable Integer employeeId) {
        List<EmployeeHistoryDto> histories = employeeHistoryService.getEmployeeHistories(employeeId);
        return ResponseEntity.ok(histories);
    }
}