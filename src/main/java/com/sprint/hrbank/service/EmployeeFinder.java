package com.sprint.hrbank.service;

import com.sprint.hrbank.entity.Employee;

public interface EmployeeFinder {

  Employee getById(Integer employeeId);
}
