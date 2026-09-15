package com.sprint.hrbank.application.employee.provided.query;

import com.sprint.hrbank.domain.employee.EmployeeStatus;
import java.time.LocalDate;

public interface EmployeeStatisticsFinder {

  Long getCount(EmployeeStatus status, LocalDate fromDate);
}
