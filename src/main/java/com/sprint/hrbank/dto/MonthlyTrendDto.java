package com.sprint.hrbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyTrendDto {
    private String yearMonth; // 년-월 (예: "2026-09")
    private long joinCount;   // 입사자 수
}