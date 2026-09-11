package com.sprint.hrbank.service.query;

import com.sprint.hrbank.dto.EmployeeDto;

public interface EmployeeFinder {

  EmployeeDto getById(Integer employeeId);
}
