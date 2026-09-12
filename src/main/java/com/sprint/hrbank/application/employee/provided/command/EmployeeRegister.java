package com.sprint.hrbank.application.employee.provided.command;

import com.sprint.hrbank.application.employee.dto.EmployeeCreateRequest;
import com.sprint.hrbank.application.employee.dto.EmployeeDto;

public interface EmployeeRegister {

  EmployeeDto register(EmployeeCreateRequest employeeCreateRequest);
}
