package com.sprint.hrbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyTrendDto {
    private String yearMonth;
    private long joinCount;
}