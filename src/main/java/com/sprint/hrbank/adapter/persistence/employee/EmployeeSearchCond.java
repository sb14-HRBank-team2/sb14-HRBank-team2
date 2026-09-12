package com.sprint.hrbank.adapter.persistence.employee;

import com.sprint.hrbank.domain.employee.EmployeeStatus;
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
