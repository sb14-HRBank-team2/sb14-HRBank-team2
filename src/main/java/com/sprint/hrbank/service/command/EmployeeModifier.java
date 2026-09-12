package com.sprint.hrbank.service.command;

import com.sprint.hrbank.dto.EmployeeDto;
import com.sprint.hrbank.dto.EmployeeUpdateRequest;

public interface EmployeeModifier {

  EmployeeDto update(Integer employeeId, EmployeeUpdateRequest request);
}
