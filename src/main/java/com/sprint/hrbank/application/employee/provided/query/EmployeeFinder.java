package com.sprint.hrbank.application.employee.provided.query;

import com.sprint.hrbank.application.employee.dto.EmployeeDto;

public interface EmployeeFinder {

  EmployeeDto getById(Integer employeeId);
}
