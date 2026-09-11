package com.sprint.hrbank.service;

import com.sprint.hrbank.dto.EmployeeCreateRequest;
import com.sprint.hrbank.dto.EmployeeDto;

public interface EmployeeRegister {

  EmployeeDto register(EmployeeCreateRequest employeeCreateRequest);
}
