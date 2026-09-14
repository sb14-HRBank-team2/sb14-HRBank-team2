package com.sprint.hrbank.adapter.persistence.employee;

import com.sprint.hrbank.domain.employee.Employee;
import java.util.List;

public interface EmployeeQRepository {

  List<Employee> search(EmployeeSearchCond employeeSearchCond);

  Long countByCondition(EmployeeSearchCond cond);
}
