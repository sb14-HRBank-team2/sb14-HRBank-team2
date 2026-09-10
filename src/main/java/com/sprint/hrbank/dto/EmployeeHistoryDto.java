package com.sprint.hrbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeHistoryDto {
    private Integer id;
    private Integer employeeId;
    private String changeContent;
    private String modifiedAt;
}