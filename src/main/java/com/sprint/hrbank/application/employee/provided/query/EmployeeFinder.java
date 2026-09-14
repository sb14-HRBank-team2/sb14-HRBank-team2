package com.sprint.hrbank.application.employee.provided.query;

import com.sprint.hrbank.domain.employee.Employee;

public interface EmployeeFinder {

  Employee getById(Integer employeeId);
}
