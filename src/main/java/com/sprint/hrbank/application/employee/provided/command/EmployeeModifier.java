package com.sprint.hrbank.application.employee.provided.command;

import com.sprint.hrbank.application.employee.dto.EmployeeDto;
import com.sprint.hrbank.application.employee.dto.EmployeeUpdateRequest;

public interface EmployeeModifier {

  EmployeeDto update(Integer employeeId, EmployeeUpdateRequest request);
}
