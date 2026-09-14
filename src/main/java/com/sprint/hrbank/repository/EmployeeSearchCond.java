package com.sprint.hrbank.repository;

import com.sprint.hrbank.entity.EmployeeStatus;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record EmployeeSearchCond(
    String nameOrEmail,
    String employeeNumber,
    String departmentName,
    String position,
    LocalDate hireDateFrom,
    LocalDate hireDateTo,
    EmployeeStatus status) {}
